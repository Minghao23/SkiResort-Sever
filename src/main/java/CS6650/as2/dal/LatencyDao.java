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
        PreparedStatement insertStmt = null;
        try {
            insertStmt = connection.prepareStatement(sql);
            insertStmt.setQueryTimeout(1);
            insertStmt.setLong(1, latency);
            insertStmt.setString(2, server);
            insertStmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                insertStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeQueryAndAddToMap(String sql, HashMap<String, Long> map, Connection connection) {
        PreparedStatement selectStmt = null;
        ResultSet rs = null;
        try {
            selectStmt = connection.prepareStatement(sql);
            rs = selectStmt.executeQuery();
            connection.commit();
            while(rs.next()) {
                map.put(rs.getString(1), rs.getLong(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                selectStmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeQueryAndAddToList(ArrayList<Long> result, Connection connection, String server, String sql) {
        PreparedStatement selectStmt = null;
        ResultSet rs = null;
        try {
            selectStmt = connection.prepareStatement(sql);
            selectStmt.setString(1, server);
            rs = selectStmt.executeQuery();
            connection.commit();
            while(rs.next()) {
                result.add(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                selectStmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
