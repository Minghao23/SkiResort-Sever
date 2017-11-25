package CS6650.as2.rest;

import CS6650.as2.model.DBAnalysis;
import CS6650.as2.model.HttpAnalysis;
import CS6650.as2.util.Stat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by hu_minghao on 10/14/17.
 */
@Path("/analysis")
public class Analysis {
    Stat stat;

    public Analysis() {
        stat = Stat.getInstance();
    }


    @GET
    @Path("http")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpAnalysis getHttpAnalysis() {
        System.out.println(stat.getHttpMeanLatency().getOrDefault("all", new Long(0)));
        System.out.println(stat.getHttp99thLatency().getOrDefault("all", new Long(0)));
        return new HttpAnalysis(
                stat.getSuccessHttpRequestNum().getOrDefault("1", new Long(0)),
                stat.getSuccessHttpRequestNum().getOrDefault("2", new Long(0)),
                stat.getSuccessHttpRequestNum().getOrDefault("3", new Long(0)),
                stat.getSuccessHttpRequestNum().getOrDefault("all", new Long(0)),
                stat.getFailNum().getOrDefault("1", new Long(0)),
                stat.getFailNum().getOrDefault("2", new Long(0)),
                stat.getFailNum().getOrDefault("3", new Long(0)),
                stat.getFailNum().getOrDefault("all", new Long(0)),
                stat.getHttpMeanLatency().getOrDefault("1", new Long(0)),
                stat.getHttpMeanLatency().getOrDefault("2", new Long(0)),
                stat.getHttpMeanLatency().getOrDefault("3", new Long(0)),
                stat.getHttpMeanLatency().getOrDefault("all", new Long(0)),
                stat.getHttpMedianLatency().getOrDefault("1", new Long(0)),
                stat.getHttpMedianLatency().getOrDefault("2", new Long(0)),
                stat.getHttpMedianLatency().getOrDefault("3", new Long(0)),
                stat.getHttpMedianLatency().getOrDefault("all", new Long(0)),
                stat.getHttp95thLatency().getOrDefault("1", new Long(0)),
                stat.getHttp95thLatency().getOrDefault("2", new Long(0)),
                stat.getHttp95thLatency().getOrDefault("3", new Long(0)),
                stat.getHttp95thLatency().getOrDefault("all", new Long(0)),
                stat.getHttp99thLatency().getOrDefault("1", new Long(0)),
                stat.getHttp99thLatency().getOrDefault("2", new Long(0)),
                stat.getHttp99thLatency().getOrDefault("3", new Long(0)),
                stat.getHttp99thLatency().getOrDefault("all", new Long(0)));
    }

    @GET
    @Path("db")
    @Produces(MediaType.APPLICATION_JSON)
    public DBAnalysis getDBAnalysis() {
        return new DBAnalysis(
                stat.getDBQueryNum().getOrDefault("1", new Long(0)),
                stat.getDBQueryNum().getOrDefault("2", new Long(0)),
                stat.getDBQueryNum().getOrDefault("3", new Long(0)),
                stat.getDBQueryNum().getOrDefault("all", new Long(0)),
                stat.getDBMeanLatency().getOrDefault("1", new Long(0)),
                stat.getDBMeanLatency().getOrDefault("2", new Long(0)),
                stat.getDBMeanLatency().getOrDefault("3", new Long(0)),
                stat.getDBMeanLatency().getOrDefault("all", new Long(0)),
                stat.getDBMedianLatency().getOrDefault("1", new Long(0)),
                stat.getDBMedianLatency().getOrDefault("2", new Long(0)),
                stat.getDBMedianLatency().getOrDefault("3", new Long(0)),
                stat.getDBMedianLatency().getOrDefault("all", new Long(0)),
                stat.getDB95thLatency().getOrDefault("1", new Long(0)),
                stat.getDB95thLatency().getOrDefault("2", new Long(0)),
                stat.getDB95thLatency().getOrDefault("3", new Long(0)),
                stat.getDB95thLatency().getOrDefault("all", new Long(0)),
                stat.getDB99thLatency().getOrDefault("1", new Long(0)),
                stat.getDB99thLatency().getOrDefault("2", new Long(0)),
                stat.getDB99thLatency().getOrDefault("3", new Long(0)),
                stat.getDB99thLatency().getOrDefault("all", new Long(0)));
    }
}
