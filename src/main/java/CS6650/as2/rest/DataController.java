package CS6650.as2.rest;

import CS6650.as2.dal.ConnectionManager;
import CS6650.as2.dal.RecordDao;
import CS6650.as2.model.MyVert;
import CS6650.as2.model.Record;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hu_minghao on 10/17/17.
 */
public class DataController {
    Connection connection;

    // Single pattern: instantiation is limited to one object.
    private static DataController instance = null;
    public static synchronized DataController getInstance() {
        if(instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    private final int[] lift_verticals = {200, 300, 400, 500};
    private List<Record> tempRecordList = new ArrayList<Record>();

    public DataController() {
        try {
            connection = new ConnectionManager().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<Record> getTempRecordList() {
        return this.tempRecordList;
    }

    public synchronized void addNewRecordToMEM(Record record) {
        tempRecordList.add(record);
    }

    public synchronized int getMEMSize() {
        return this.tempRecordList.size();
    }


    public synchronized void updateDB() {
        try {
            RecordDao.getInstance().batchCreate(connection, tempRecordList);
            tempRecordList.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getVerticalByLift(int liftID) {
        if (liftID <= 10) {
            return lift_verticals[0];
        }else if (liftID <= 20) {
            return lift_verticals[1];
        }else if (liftID <= 30) {
            return lift_verticals[2];
        }else{
            return lift_verticals[3];
        }
    }

    public MyVert getMyVerticalFromDB(int skierID, int dayNum) {
        List<Record> records = new ArrayList<Record>();
        try {
            records = RecordDao.getInstance().getRecordsBySkierIDAndDayNum(connection, skierID, dayNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int liftTimes = records.size();
        int totalVertical = 0;
        for (int i = 0; i < liftTimes; i++) {
            totalVertical += getVerticalByLift(records.get(i).getLiftID());
        }
        return new MyVert(totalVertical,liftTimes);
    }

    public MyVert getMyVerticalFromMEM(int skierID, int dayNum) {
        int liftTimes = 0;
        int totalVertical = 0;
        for (int i = 0; i < tempRecordList.size(); i++) {
            if(tempRecordList.get(i).getSkierID() == skierID) {
                liftTimes++;
                totalVertical += getVerticalByLift(tempRecordList.get(i).getLiftID());
            }
        }
        return new MyVert(totalVertical,liftTimes);
    }

}
