package CS6650.as2.dal;

import CS6650.as2.model.Record;

import java.sql.*;
import java.util.concurrent.Callable;

/**
 * Created by hu_minghao on 10/23/17.
 */
public class InsertTask implements Callable<Object> {
    Connection connection;
    Record record;

    public InsertTask(Connection connection, Record record) {
        this.connection = connection;
        this.record = record;
    }

    public Object call() {
        String insertRecord = "INSERT Record(SkierID,LiftID,DayNum,Time) VALUE (?,?,?,?);";
        PreparedStatement insertStmt = null;
        ResultSet results = null;
        try {
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
            insertStmt.close();
            results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
