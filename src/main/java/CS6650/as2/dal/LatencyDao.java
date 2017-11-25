package CS6650.as2.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hu_minghao on 11/23/17.
 */
public abstract class LatencyDao {
    public abstract ArrayList<Long> getLatencies(Connection connection, String server) throws SQLException;
    public abstract HashMap<String, Long> getMeanLatency(Connection connection) throws SQLException;

    public void executeInsert(Connection connection, long latency, String server, String sql) {
        try {
            PreparedStatement insertStmt = connection.prepareStatement(sql);
            insertStmt.setLong(1, latency);
            insertStmt.setString(2, server);
            insertStmt.executeUpdate();
            insertStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQueryAndAddToMap(String sql, HashMap<String, Long> map, Connection connection) {
        try {
            PreparedStatement selectStmt = connection.prepareStatement(sql);
            ResultSet rs = selectStmt.executeQuery();
            while(rs.next()) {
                map.put(rs.getString(1), rs.getLong(2));
            }
            selectStmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQueryAndAddToList(ArrayList<Long> result, Connection connection, String server, String sql) {
        try {
            PreparedStatement selectStmt = connection.prepareStatement(sql);
            selectStmt.setString(1, server);
            ResultSet rs = selectStmt.executeQuery();
            while(rs.next()) {
                result.add(rs.getLong(1));
            }
            selectStmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
