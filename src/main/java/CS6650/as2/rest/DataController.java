package CS6650.as2.rest;

import CS6650.as2.dal.ConnectionManager;
import CS6650.as2.dal.MyVertDao;
import CS6650.as2.model.MyVert;
import CS6650.as2.model.Record;
import CS6650.as2.util.Stat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;

/**
 * Created by hu_minghao on 10/17/17.
 */
public class DataController {

    // Single pattern: instantiation is limited to one object.
    private static DataController instance = null;
    public static synchronized DataController getInstance() {
        if(instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    ExecutorService recordTaskPoolExecutor;
    ExecutorService myVertTaskPoolExecutor;
    Connection connectionForRecord;
    Connection connectionForMyVert;
    Connection connectionForGET;

    public DataController() {
        try {
            connectionForRecord = new ConnectionManager().getConnection();
            connectionForMyVert = new ConnectionManager().getConnection();
            connectionForGET = new ConnectionManager().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recordTaskPoolExecutor = Executors.newFixedThreadPool(100);
        myVertTaskPoolExecutor = Executors.newSingleThreadExecutor();
    }

    public void addPostTaskToWorkQueue(Record record) {
        recordTaskPoolExecutor.submit(new RecordTask(connectionForRecord, record));
        myVertTaskPoolExecutor.submit(new MyVertTask(connectionForMyVert, record));
    }

    public MyVert getMyVerticalFromDB(int skierID, int dayNum) {
        MyVert myVert = null;
        try {
            long startTime = System.currentTimeMillis();
            myVert = MyVertDao.getInstance().getMyVert(connectionForGET, skierID, dayNum);
            Stat.getInstance().recordDBLatency(System.currentTimeMillis() - startTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return myVert;
    }
}
