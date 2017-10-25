package CS6650.as2.rest;

import CS6650.as2.dal.ConnectionManager;
import CS6650.as2.dal.MyVertDao;
import CS6650.as2.model.MyVert;
import CS6650.as2.model.Record;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.LinkedTransferQueue;

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

    private LinkedTransferQueue<Record> tempRecordList;
    Connection connection;

    public DataController() {
        tempRecordList = new LinkedTransferQueue<Record>();
        try {
            connection = new ConnectionManager().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        InsertToDB insertToDB = new InsertToDB(connection, tempRecordList);
        new Thread(insertToDB).start();
    }

    public synchronized LinkedTransferQueue<Record> getTempRecordList() {
        return this.tempRecordList;
    }

    public synchronized void addNewRecordToQueue(Record record) {
        tempRecordList.offer(record);
    }

    public synchronized int getQueueSize() {
        return this.tempRecordList.size();
    }


//    public synchronized void updateDB() {
//        try {
//            RecordDao.getInstance().create(connection, tempRecordList);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }



    public MyVert getMyVerticalFromDB(int skierID, int dayNum) {
        MyVert myVert = null;
        try {
            myVert = MyVertDao.getInstance().getMyVert(connection, skierID, dayNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return myVert;
    }

//    public MyVert getMyVerticalFromMEM(int skierID, int dayNum) {
//        int liftTimes = 0;
//        int totalVertical = 0;
//        for (int i = 0; i < tempRecordList.size(); i++) {
//            if(tempRecordList.get(i).getSkierID() == skierID) {
//                liftTimes++;
//                totalVertical += getVerticalByLift(tempRecordList.get(i).getLiftID());
//            }
//        }
//        return new MyVert(totalVertical,liftTimes);
//    }

}
