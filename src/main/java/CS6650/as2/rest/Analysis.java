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
        return new HttpAnalysis(stat.getHttpRequestNum(),
                stat.getFailNum(),
                stat.getHttpMeanLatency(),
                stat.getHttpMedianLatency(),
                stat.getHttp95thLatency(),
                stat.getHttp99thLatency());
    }

    @GET
    @Path("db")
    @Produces(MediaType.APPLICATION_JSON)
    public DBAnalysis getDBAnalysis() {
        return new DBAnalysis(stat.getDBQueryNum(),
                stat.getDBMeanLatency(),
                stat.getDBMedianLatency(),
                stat.getDB95thLatency(),
                stat.getDB99thLatency());
    }

    @GET
    @Path("clear")
    public void clear() {
        stat.clearAll();
    }

}
