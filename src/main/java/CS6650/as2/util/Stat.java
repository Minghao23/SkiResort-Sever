package CS6650.as2.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private int failNum = 0;
    private long totalHttpLatency = 0;
    private long totalDBLatency = 0;
    private boolean isHttpSorted = false;
    private boolean isDBSorted = false;
    private List<Long> httpLatencies = new ArrayList<Long>();
    private List<Long> DBLatencies = new ArrayList<Long>();
//    private final String HttpFile = "";
//    private final String DBFile = "";


//    synchronized public void writeToFile(String FileURL, long content) {
//        try {
//            FileWriter writer = new FileWriter(FileURL, true);
//            writer.write("" + content + "\n");
//            writer.close();
//        }catch(IOException ioe){
//            ioe.printStackTrace();
//        }
//    }
    synchronized public void recordHttpLatency(long latency) {
        totalHttpLatency += latency;
        httpLatencies.add(latency);
        isHttpSorted = false;
    }

    synchronized public void recordDBLatency(long latency) {
        totalDBLatency += latency;
        DBLatencies.add(latency);
        isDBSorted = false;
    }

    synchronized public void recordFailNum() {
        failNum++;
    }

    public int getFailNum() {
        return failNum;
    }

    public int getHttpRequestNum() {
        return httpLatencies.size() + failNum;
    }

    public int getDBQueryNum() {
        return DBLatencies.size();
    }

    public void clearAll() {
        failNum = 0;
        totalHttpLatency = 0;
        totalDBLatency = 0;
        isHttpSorted = false;
        isDBSorted = false;
        httpLatencies.clear();
        DBLatencies.clear();
    }

    public long getMeanLatency(long totalLatency, int num) {
        if(num == 0) {
            return 0;
        }
        return totalLatency / num;
    }

    public long getMedianLatency(List<Long> latencies, boolean isSorted) {
        if (!isSorted) {
            Collections.sort(latencies);
            isSorted = true;
        }

        return latencies.get(latencies.size() / 2);
    }

    public long get95thLatency(List<Long> latencies, boolean isSorted) {
        if (!isSorted) {
            Collections.sort(latencies);
            isSorted = true;
        }

        return latencies.get((int)Math.floor(latencies.size() * 0.95));
    }

    public long get99thLatency(List<Long> latencies, boolean isSorted) {
        if (!isSorted) {
            Collections.sort(latencies);
            isSorted = true;
        }

        return latencies.get((int)Math.floor(latencies.size() * 0.99));
    }

    // Stat of Http requests
    public long getHttpMeanLatency() {
        return getMeanLatency(totalHttpLatency, this.httpLatencies.size());
    }

    public long getHttpMedianLatency() {
        return getMedianLatency(this.httpLatencies, this.isHttpSorted);
    }
    public long getHttp95thLatency() {
        return get95thLatency(this.httpLatencies, this.isHttpSorted);
    }
    public long getHttp99thLatency() {
        return get99thLatency(this.httpLatencies, this.isHttpSorted);
    }

    // Stat of DB queries
    public long getDBMeanLatency() {
        return getMeanLatency(totalDBLatency, this.DBLatencies.size());
    }

    public long getDBMedianLatency() {
        return getMedianLatency(this.DBLatencies, this.isDBSorted);
    }
    public long getDB95thLatency() {
        return get95thLatency(this.DBLatencies, this.isDBSorted);
    }
    public long getDB99thLatency() {
        return get99thLatency(this.DBLatencies, this.isDBSorted);
    }
}

