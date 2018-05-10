package cn.code.chameleon.monitor.aspect;

import cn.code.chameleon.monitor.actions.context.ActionContext;
import cn.code.chameleon.monitor.actions.invoker.Invoker;
import cn.code.chameleon.monitor.pojo.ServiceLogWithBLOBs;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.SERVICE_INVOKER;

/**
 * @author liumingyu
 * @create 2018-01-20 下午5:01
 */
@Component
@Aspect
public class ServiceAspect {

    @Resource(name = SERVICE_INVOKER)
    private Invoker<ServiceLogWithBLOBs> invoker;

    @Pointcut("@annotation(cn.code.chameleon.monitor.annotation.ServiceMethod)")
    public void annotationPointCut(){}


    @Pointcut("execution(public * cn.code.chameleon.service..*.save*(..))")
    public void saveMethodPointcut(){}

    @Pointcut("execution(public * cn.code.chameleon.service..*.update*(..))")
    public void updateMethodPointcut(){}

    @Pointcut("execution(public * cn.code.chameleon.service..*.delete*(..))")
    public void deleteMethodPointcut(){}

    @Pointcut("execution(public * cn.code.chameleon.service..*.batch*(..))")
    public void batchMethodPointcut(){}

    /** 需排除自己的log */
    @Pointcut("execution(public * cn.code.chameleon.monitor.service..*.*(..))")
    public void servicelogServicePointcut(){}

    @Around(value = "(annotationPointCut() || saveMethodPointcut() || updateMethodPointcut() || deleteMethodPointcut() " +
            "|| batchMethodPointcut()) && !servicelogServicePointcut()")
    public Object serviceMethodAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        ActionContext<ServiceLogWithBLOBs> actionContext = ActionContext.<ServiceLogWithBLOBs>builder()
                .invokerName(SERVICE_INVOKER)
                .joinPoint(joinPoint)
                .build();
        //使用service方法对应的invoker执行对应的command
        invoker.invoke(actionContext);
        if (actionContext.getMethodException() != null){
            throw actionContext.getMethodException();
        }
        return actionContext.getMethodResult();
    }
}
