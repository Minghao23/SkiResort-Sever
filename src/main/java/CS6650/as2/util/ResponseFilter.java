package CS6650.as2.util;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by hu_minghao on 11/24/17.
 */
@LatencyLog
@Provider
public class ResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext,
                       ContainerResponseContext containerResponseContext) throws IOException {
       Stat.getInstance().recordHttpLatency(System.currentTimeMillis() - (long)containerRequestContext.getProperty("startTime"));
    }
}
