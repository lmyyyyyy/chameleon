package cn.code.chameleon.monitor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liumingyu
 * @create 2018-01-20 下午4:54
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Command {

    int[] order() default ORDER_LAST;

    String[] invoker();

    int ORDER_INIT = 0;
    int ORDER_START = 1;
    int ORDER_MIDDLE = 2;
    int ORDER_LAST = Integer.MAX_VALUE - 1;
    int ORDER_FINNALY = Integer.MAX_VALUE;
}
