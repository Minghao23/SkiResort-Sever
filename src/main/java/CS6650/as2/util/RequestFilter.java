package CS6650.as2.util;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by hu_minghao on 11/24/17.
 */
@LatencyLog
@Provider  //不知道为什么和后请求过滤器冲突,不能同时使用
public class RequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        containerRequestContext.setProperty("startTime", System.currentTimeMillis());
    }
}
