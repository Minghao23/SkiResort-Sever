package CS6650.as2.dal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hu_minghao on 10/17/17.
 */
public class DBLatencyDao extends LatencyDao{

    // Single pattern: instantiation is limited to one object.
    private static DBLatencyDao instance = null;
    public static synchronized DBLatencyDao getInstance() {
        if(instance == null) {
            instance = new DBLatencyDao();
        }
        return instance;
    }

    public void create(Connection connection, long latency, String server) throws SQLException {
        String insert = "INSERT DBLatency(Latency, Server) VALUE (?, ?);";
        executeInsert(connection, latency, server, insert);
    }

    public HashMap<String, Long> getQueryNum(Connection connection) throws SQLException {
        HashMap<String, Long> result = new HashMap<>();
        String select = "SELECT Server, COUNT(*) AS CNT FROM DBLatency GROUP BY Server " +
                "UNION SELECT 'all' AS Server, COUNT(*) AS CNT FROM DBLatency;";
        executeQueryAndAddToMap(select, result, connection);
        return result;
    }

    public HashMap<String, Long> getMeanLatency(Connection connection) throws SQLException {
        HashMap<String, Long> result = new HashMap<>();
        String select = "SELECT Server, AVG(Latency) AS Mean FROM DBLatency GROUP BY Server " +
                "UNION SELECT 'all' AS Server, AVG(Latency) AS Mean FROM DBLatency;";
        executeQueryAndAddToMap(select, result, connection);
        return result;
    }

    public ArrayList<Long> getLatencies(Connection connection, String server) throws SQLException {
        ArrayList<Long> result = new ArrayList<>();
        String select = "SELECT Latency FROM DBLatency WHERE Server=? ORDER BY CAST(`Latency` AS DECIMAL) ASC;" ;
        executeQueryAndAddToList(result, connection, server, select);
        return result;
    }

}
