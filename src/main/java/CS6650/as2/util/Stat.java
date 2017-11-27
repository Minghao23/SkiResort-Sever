package CS6650.as2.util;

import CS6650.as2.dal.ConnectionManager;
import CS6650.as2.dal.DBLatencyDao;
import CS6650.as2.dal.HttpLatencyDao;
import CS6650.as2.dal.LatencyDao;
import CS6650.as2.rest.StatTask;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hu_minghao on 11/22/17.
 */
public class Stat {

    // Single pattern: instantiation is limited to one object.
    private static Stat instance = null;
    public static synchronized Stat getInstance() {
        if(instance == null) {
            instance = new Stat();
        }
        return instance;
    }

    ExecutorService statTaskPoolExecutor;
    Connection connectionForAnalysis;
    HttpLatencyDao httpLatencyDao;
    DBLatencyDao dbLatencyDao;
    final String SERVER = "1"; // Change this field to mark the server number

    public Stat() {
        try {
            connectionForAnalysis = new ConnectionManager().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        statTaskPoolExecutor = Executors.newFixedThreadPool(100);
        httpLatencyDao = HttpLatencyDao.getInstance();
        dbLatencyDao = DBLatencyDao.getInstance();
    }

    public void recordHttpLatency(long latency) {
        statTaskPoolExecutor.submit(new StatTask(connectionForAnalysis, latency, false, SERVER));
    }

    public void recordDBLatency(long latency) {
        try {
            dbLatencyDao.create(connectionForAnalysis, latency, SERVER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void recordFailNum() {
        statTaskPoolExecutor.submit(new StatTask(connectionForAnalysis, 0, true, SERVER));
    }

    public HashMap<String, Long> getFailNum() {
        HashMap<String, Long> result = new HashMap<>();
        try {
            result = httpLatencyDao.getFailedRequestNum(connectionForAnalysis);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public HashMap<String, Long> getSuccessHttpRequestNum() {
        HashMap<String, Long> result = new HashMap<>();
        try {
            result = httpLatencyDao.getSuccessRequestNum(connectionForAnalysis);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public HashMap<String, Long> getDBQueryNum() {
        HashMap<String, Long> result = new HashMap<>();
        try {
            result = dbLatencyDao.getQueryNum(connectionForAnalysis);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public HashMap<String, Long> getMeanLatency(LatencyDao dao) {
        HashMap<String, Long> result = new HashMap<>();
        try {
            result = dao.getMeanLatency(connectionForAnalysis);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void getLatenciesAndCalculatePercent(HashMap<String, Long> result, LatencyDao dao, double percent) {
        try {
            ArrayList<Long> latencies1 = dao.getLatencies(connectionForAnalysis, "1");
            result.put("1", latencies1.isEmpty() ? new Long(0) : latencies1.get((int)Math.floor(latencies1.size() * percent)));
            ArrayList<Long> latencies2 = dao.getLatencies(connectionForAnalysis, "2");
            result.put("2", latencies2.isEmpty() ? new Long(0) : latencies2.get((int)Math.floor(latencies2.size() * percent)));
            ArrayList<Long> latencies3 = dao.getLatencies(connectionForAnalysis, "3");
            result.put("3", latencies3.isEmpty() ? new Long(0) : latencies3.get((int)Math.floor(latencies3.size() * percent)));
            ArrayList<Long> latenciesAll = new ArrayList<>();
            latenciesAll.addAll(latencies1);
            latenciesAll.addAll(latencies2);
            latenciesAll.addAll(latencies3);
            result.put("all", latenciesAll.isEmpty() ? new Long(0) : latenciesAll.get((int)Math.floor(latenciesAll.size() * percent)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public HashMap<String, Long> getMedianLatency(LatencyDao dao) {
        HashMap<String, Long> result = new HashMap<>();
        getLatenciesAndCalculatePercent(result, dao, 0.5);
        return result;
    }

    public HashMap<String, Long> get95thLatency(LatencyDao dao) {
        HashMap<String, Long> result = new HashMap<>();
        getLatenciesAndCalculatePercent(result, dao, 0.95);
        return result;
    }

    public HashMap<String, Long> get99thLatency(LatencyDao dao) {
        HashMap<String, Long> result = new HashMap<>();
        getLatenciesAndCalculatePercent(result, dao, 0.99);
        return result;
    }

    // Stat of Http requests
    public HashMap<String, Long> getHttpMeanLatency() {
        return getMeanLatency(httpLatencyDao);
    }
    public HashMap<String, Long> getHttpMedianLatency() {
        return getMedianLatency(httpLatencyDao);
    }
    public HashMap<String, Long> getHttp95thLatency() {
        return get95thLatency(httpLatencyDao);
    }
    public HashMap<String, Long> getHttp99thLatency() {
        return get99thLatency(httpLatencyDao);
    }

    // Stat of DB queries
    public HashMap<String, Long> getDBMeanLatency() {
        return getMeanLatency(dbLatencyDao);
    }
    public HashMap<String, Long> getDBMedianLatency() {
        return getMedianLatency(dbLatencyDao);
    }
    public HashMap<String, Long> getDB95thLatency() {
        return get95thLatency(dbLatencyDao);
    }
    public HashMap<String, Long> getDB99thLatency() {
        return get99thLatency(dbLatencyDao);
    }
}