package CS6650.as2.dal;

import CS6650.as2.model.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public void batchCreate(Connection connection, List<Record> records) throws SQLException {
        List<InsertTask> insertTasks = new ArrayList<InsertTask>();
        try {
            for(Record record : records) {
                insertTasks.add(new InsertTask(connection,record));
            }
            ExecutorService pool = Executors.newFixedThreadPool(100);
            pool.invokeAll(insertTasks);
            pool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Record> getRecordsBySkierIDAndDayNum(Connection connection, int skierID, int dayNum) throws SQLException {
        List<Record> records = new ArrayList<Record>();
        String selectRecords = "SELECT * FROM Record WHERE SkierID=? AND DayNum=?;";
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
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
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
        return records;
    }

}
