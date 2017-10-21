package CS6650.as2.dal;

import CS6650.as2.model.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hu_minghao on 10/17/17.
 */
public class RecordDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static RecordDao instance = null;
    protected RecordDao() {
        connectionManager = new ConnectionManager();
    }
    public static RecordDao getInstance() {
        if(instance == null) {
            instance = new RecordDao();
        }
        return instance;
    }

    public Record create(Record record) throws SQLException {
        String insertRecord = "INSERT Record(SkierID,LiftID,DayNum,Time) VALUE (?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRecord,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, record.getSkierID());
            insertStmt.setInt(2, record.getLiftID());
            insertStmt.setInt(3, record.getDayNum());
            insertStmt.setInt(4, record.getTime());
            insertStmt.executeUpdate();

            results = insertStmt.getGeneratedKeys();
            int recordID = -1;
            if(results.next()) {
                recordID = results.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            record.setRecordID(recordID);
            return record;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(insertStmt != null) {
                insertStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
    }

    public List<Record> getRecordsBySkierIDAndDayNum(int skierID, int dayNum) throws SQLException {
        List<Record> records = new ArrayList<Record>();
        String selectRecords = "SELECT * FROM Record WHERE SkierID=? AND DayNum=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRecords);
            selectStmt.setInt(1, skierID);
            selectStmt.setInt(2, dayNum);
            results = selectStmt.executeQuery();
            while(results.next()) {
                int recordID = results.getInt("RecordID");
                int liftID = results.getInt("LiftID");

                Record record = new Record(recordID, skierID, liftID, dayNum);
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
        return records;
    }

    public Record delete(Record record) throws SQLException{
        String deleteRecord = "DELETE FROM Record WHERE RecordID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteRecord);
            deleteStmt.setInt(1, record.getRecordID());
            deleteStmt.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }

}
