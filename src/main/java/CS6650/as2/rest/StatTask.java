package CS6650.as2.rest;

import CS6650.as2.dal.HttpLatencyDao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hu_minghao on 10/24/17.
 */
public class StatTask implements Runnable {
    public Connection connection;
    public long latency;
    public boolean isFailed;
    public String server;

    public StatTask(Connection connection, long latency, boolean isFailed, String server) {
        this.connection = connection;
        this.latency = latency;
        this.isFailed = isFailed;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            if(isFailed) {
                HttpLatencyDao.getInstance().updateHttpFail(connection, server);
            }else {
                HttpLatencyDao.getInstance().create(connection, latency, server);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
