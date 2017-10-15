package CS6650.as2.rest;

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

    @POST
    @Path("post_test")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public String postTest() {
        return "hello jersey! Post!";
    }

    @GET
    @Path("myvert/{skierID}&{dayNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getVert() {
        return "hello jersey! Get!";
    }

    @POST
    @Path("load")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public String load() {
        return "hello jersey! Post!";
    }
}
