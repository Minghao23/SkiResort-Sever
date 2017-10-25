package CS6650.as2.rest;

import CS6650.as2.dal.MyVertDao;
import CS6650.as2.model.Record;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hu_minghao on 10/24/17.
 */
public class MyVertTask implements Runnable {
    public Connection connection;
    public Record record;

    public MyVertTask(Connection connection, Record record) {
        this.connection = connection;
        this.record = record;
    }

    @Override
    public void run() {
        try {
            MyVertDao.getInstance().update(connection, record);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
