package CS6650.as2.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hu_minghao on 10/17/17.
 */
public class HttpLatencyDao extends LatencyDao{

    // Single pattern: instantiation is limited to one object.
    private static HttpLatencyDao instance = null;
    public static synchronized HttpLatencyDao getInstance() {
        if(instance == null) {
            instance = new HttpLatencyDao();
        }
        return instance;
    }

    public void create(Connection connection, long latency, String server) throws SQLException {
        String insert = "INSERT HttpLatency(Latency, Server) VALUE (?, ?);";
        executeInsert(connection, latency, server, insert);
    }

    public void updateHttpFail(Connection connection, String server) throws SQLException {
        String update = "UPDATE HttpFail SET FailNum=FailNum+1 WHERE Server=?;";
        try {
            PreparedStatement updateStmt = connection.prepareStatement(update);
            updateStmt.setString(1, server);
            updateStmt.executeUpdate();
            updateStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public HashMap<String, Long> getSuccessRequestNum(Connection connection) throws SQLException {
        HashMap<String, Long> result = new HashMap<>();
        String select = "SELECT Server, COUNT(*) AS CNT FROM HttpLatency GROUP BY Server " +
                "UNION SELECT 'all' AS Server, COUNT(*) AS CNT FROM HttpLatency;";
        executeQueryAndAddToMap(select, result, connection);
        return result;
    }

    public HashMap<String, Long> getFailedRequestNum(Connection connection) throws SQLException {
        HashMap<String, Long> result = new HashMap<>();
        String select = "SELECT Server, FailNum FROM HttpFail " +
                "UNION SELECT 'all' AS Server, SUM(FailNum) AS FailNum FROM HttpFail;";
        executeQueryAndAddToMap(select, result, connection);
        return result;
    }

    public HashMap<String, Long> getMeanLatency(Connection connection) throws SQLException {
        HashMap<String, Long> result = new HashMap<>();
        String select = "SELECT Server, AVG(Latency) AS Mean FROM HttpLatency GROUP BY Server " +
                "UNION SELECT 'all' AS Server, AVG(Latency) AS Mean FROM HttpLatency;";
        executeQueryAndAddToMap(select, result, connection);
        return result;
    }

    public ArrayList<Long> getLatencies(Connection connection, String server) throws SQLException {
        ArrayList<Long> result = new ArrayList<>();
        String select = "SELECT Latency FROM HttpLatency WHERE Server=? ORDER BY CAST(`Latency` AS DECIMAL) ASC;" ;
        executeQueryAndAddToList(result, connection, server, select);
        return result;
    }

}
