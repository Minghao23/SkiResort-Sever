package CS6650.as2.util;

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hu_minghao on 11/24/17.
 */
@NameBinding // Bind NameBinding annotation at APIs
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface LatencyLog { // Used for registering filters
}