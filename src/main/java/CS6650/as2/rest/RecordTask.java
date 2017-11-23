package CS6650.as2.rest;

import CS6650.as2.dal.RecordDao;
import CS6650.as2.model.Record;
import CS6650.as2.util.Stat;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hu_minghao on 10/24/17.
 */
public class RecordTask implements Runnable {
    public Connection connection;
    public Record record;

    public RecordTask(Connection connection, Record record) {
        this.connection = connection;
        this.record = record;
    }

    @Override
    public void run() {
        try {
            long startTime = System.currentTimeMillis();
            RecordDao.getInstance().create(connection, record);
            Stat.getInstance().recordDBLatency(System.currentTimeMillis() - startTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
