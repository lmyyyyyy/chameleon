package cn.code.chameleon.monitor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liumingyu
 * @create 2018-01-20 下午5:15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MapperMethod {
    /**
     * 表名
     * @return
     */
    String table() default "";

    /**
     * mapper方法的操作类型
     */
    enum OperationType {INSERT,DELETE,UPDATE};

    /**
     * 操作类型
     * @return
     */
    OperationType operationType() default OperationType.UPDATE;

    /**
     * 该对象的主键字段
     * @return
     */
    String primaryKey() default "id";

    /**
     * mapper方法对应的myBatis sql id
     * @return
     */
    String sqlId() default "";
}
