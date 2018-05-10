package cn.code.chameleon.monitor.util;

import cn.code.chameleon.monitor.annotation.MapperMethod;
import cn.code.chameleon.monitor.annotation.ServiceMethod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.aop.framework.AopProxyUtils;

import java.lang.reflect.Method;

/**
 * @author liumingyu
 * @create 2018-01-21 下午4:34
 */
public class AnnotationUtil {

    /**
     * 获取service方法注解
     *
     * @param joinPoint
     * @return
     */
    public static ServiceMethod getServiceMethodAnnotation(JoinPoint joinPoint) {
        Class targetClass = getServiceClass(joinPoint);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method;
        try {
            method = targetClass.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        } catch (NoSuchMethodException e) {
            method = methodSignature.getMethod();
        }
        return method.getAnnotation(ServiceMethod.class);
    }

    /**
     * 获取service类注解
     *
     * @param joinPoint
     * @return
     */
    public static ServiceMethod getServiceClassAnnotation(JoinPoint joinPoint) {
        //先获取接口上的
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        ServiceMethod annotation = null;
        //从接口获取
        annotation = (ServiceMethod) methodSignature.getDeclaringType().getAnnotation(ServiceMethod.class);
        if (annotation == null) {
            //从类上获取
            Class targetClass = getServiceClass(joinPoint);
            annotation = (ServiceMethod) targetClass.getAnnotation(ServiceMethod.class);
        }
        return annotation;
    }

    /**
     * 获取apo代理对象对应的类型
     *
     * @param joinPoint
     * @return
     */
    public static Class getServiceClass(JoinPoint joinPoint) {
        return AopProxyUtils.ultimateTargetClass(joinPoint.getThis());
    }

    /**
     * 获取MapperMethod注解
     *
     * @param joinPoint
     * @return
     */
    public static MapperMethod getMapperAnnotation(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        MapperMethod daoMethod = null;

        if ((daoMethod = methodSignature.getMethod().getAnnotation(MapperMethod.class)) == null) {
            daoMethod = (MapperMethod) methodSignature.getDeclaringType().getAnnotation(MapperMethod.class);
        }

        return daoMethod;
    }

    /**
     * 获取Mapper对象对应的接口
     *
     * @param joinPoint
     * @return
     */
    public static Class getMapperInterface(JoinPoint joinPoint) {
        if (joinPoint instanceof MethodInvocationProceedingJoinPoint) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            return methodSignature.getDeclaringType();
        }
        return Object.class;
    }
}
