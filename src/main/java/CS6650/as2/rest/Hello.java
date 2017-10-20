package CS6650.as2.rest;

import CS6650.as2.dal.RecordDao;
import CS6650.as2.model.MyVert;
import CS6650.as2.model.Record;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

/**
 * Created by hu_minghao on 10/14/17.
 */
@Path("/hello")
public class Hello {
    DataController dataController = new DataController();

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTest() {
        return "Welcome to Blackler-Whistcomb Ski Resort!";
    }

    @GET
    @Path("myvert/{skierID}&{dayNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public MyVert getRecord(
            @PathParam("skierID") int skierID,
            @PathParam("dayNum") int dayNum) {
        return dataController.getMyVertical(skierID, dayNum);
    }

    @POST
    @Path("load")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String load(Record data) {
        try {
            RecordDao.getInstance().create(new Record(data.getSkierID(), data.getLiftID(), data.getDayNum(), data.getTime()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Load>>> " + data.toString();
    }
}
