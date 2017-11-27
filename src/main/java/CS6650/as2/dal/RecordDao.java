package CS6650.as2.dal;

import CS6650.as2.model.Record;

import java.sql.*;

/**
 * Created by hu_minghao on 10/17/17.
 */
public class RecordDao {

    // Single pattern: instantiation is limited to one object.
    private static RecordDao instance = null;
    public static synchronized RecordDao getInstance() {
        if(instance == null) {
            instance = new RecordDao();
        }
        return instance;
    }

    public void create(Connection connection, Record record) throws SQLException {
        String insertRecord = "INSERT Record(SkierID,LiftID,DayNum,Time) VALUE (?,?,?,?);";
        PreparedStatement insertStmt = null;
        try {
            insertStmt = connection.prepareStatement(insertRecord,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, record.getSkierID());
            insertStmt.setInt(2, record.getLiftID());
            insertStmt.setInt(3, record.getDayNum());
            insertStmt.setInt(4, record.getTime());
            insertStmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            insertStmt.close();
        }
    }
}
