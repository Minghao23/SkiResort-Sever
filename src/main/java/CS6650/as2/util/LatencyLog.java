package CS6650.as2.util;

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hu_minghao on 11/24/17.
 */
@NameBinding //用于定义一个运行时的自定义注解
@Target({ElementType.TYPE,ElementType.METHOD}) //作用范围类和方法
@Retention(value = RetentionPolicy.RUNTIME)  //运行时有效
public @interface LatencyLog {
}