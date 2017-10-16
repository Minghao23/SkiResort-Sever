package CS6650.as2.rest;

import CS6650.as2.model.RFIDLiftData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by hu_minghao on 10/14/17.
 */
@Path("/hello")
public class Hello {
    @GET
    @Path("get_test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTest() {
        return "hello jersey! Get!";
    }

    @GET
    @Path("myvert/{skierID}&{dayNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getVert() {
        return "hello jersey! Get!";
    }

    @POST
    @Path("load")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String load(RFIDLiftData data) {
        return "Load " + data.toString();
    }

    @POST
    @Path("test_post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String load2(String s) {
        return "haha " + s;
    }
}
