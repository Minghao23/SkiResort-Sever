package CS6650.as2.rest;

import CS6650.as2.dal.MyVertDao;
import CS6650.as2.dal.RecordDao;
import CS6650.as2.model.Record;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by hu_minghao on 10/24/17.
 */
public class InsertToDB implements Runnable {
    public Connection connection;
    public LinkedTransferQueue<Record> tempRecordList;

    public InsertToDB(Connection connection, LinkedTransferQueue<Record> tempRecordList) {
        this.connection = connection;
        this.tempRecordList = tempRecordList;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Record record = tempRecordList.take();
                RecordDao.getInstance().create(connection, record);
                MyVertDao.getInstance().update(connection, record);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
