package CS6650.as2.rest;

import CS6650.as2.model.MyVert;
import CS6650.as2.model.Record;
import CS6650.as2.util.LatencyLog;
import CS6650.as2.util.Stat;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by hu_minghao on 10/14/17.
 */
@Path("/hello")
public class Hello extends ResourceConfig {
    DataController dataController;

    public Hello() {
        dataController = DataController.getInstance();
        packages("CS6650.as2.util");
    }


    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTest() {
        return "Welcome to Blackler-Whistcomb Ski Resort!";
    }

    @LatencyLog
    @GET
    @Path("myvert/{skierID}&{dayNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public MyVert getMyVert(
            @PathParam("skierID") int skierID,
            @PathParam("dayNum") int dayNum) {
        return dataController.getMyVerticalFromDB(skierID, dayNum);
    }

    @LatencyLog
    @POST
    @Path("load")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String load(Record data) {
        try{
            dataController.addPostTaskToWorkQueue(new Record(data.getSkierID(), data.getLiftID(), data.getDayNum(), data.getTime()));
        }catch (Exception e) {
            Stat.getInstance().recordFailNum();
        }
        return "Load>>> " + data.toString();
    }

}
