package CS6650.as2.rest;

import CS6650.as2.model.MyVert;
import CS6650.as2.model.Record;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by hu_minghao on 10/14/17.
 */
@Path("/hello")
public class Hello {
    DataController dataController;

    public Hello() {
        dataController = DataController.getInstance();
    }

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTest() {
        return "Welcome to Blackler-Whistcomb Ski Resort!";
    }


    @GET
    @Path("myvert/{skierID}&{dayNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public MyVert getMyVert(
            @PathParam("skierID") int skierID,
            @PathParam("dayNum") int dayNum) {
        return dataController.getMyVerticalFromDB(skierID, dayNum);
    }

    @POST
    @Path("load")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String load(Record data) {
        dataController.addTaskToWorkQueue(new Record(data.getSkierID(), data.getLiftID(), data.getDayNum(), data.getTime()));
        return "Load>>> " + data.toString();
    }

}
