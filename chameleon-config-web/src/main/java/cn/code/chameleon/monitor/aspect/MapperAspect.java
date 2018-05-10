package cn.code.chameleon.monitor.aspect;

import cn.code.chameleon.monitor.actions.context.ActionContext;
import cn.code.chameleon.monitor.actions.invoker.Invoker;
import cn.code.chameleon.monitor.pojo.MapperLogWithBLOBs;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.MAPPER_INVOKER;

/**
 * @author liumingyu
 * @create 2018-01-20 下午5:01
 */
@Component
@Aspect
public class MapperAspect {
    @Resource(name = MAPPER_INVOKER)
    private Invoker<MapperLogWithBLOBs> invoker;

    /**
     * 在mapper中，返回类型为int的方法为增删改方法
     */
    @Pointcut("execution(public int cn.code.chameleon.mapper..*.*(..))")
    public void modifyOperationPointCut(){}

    /**
     * mapper日志本身的mapper方法
     */
    @Pointcut("execution(public * cn.code.chameleon.monitor.mapper.*.*(..))")
    public void mapperLogOperation(){}

    @Around("modifyOperationPointCut() && !mapperLogOperation()")
    public Object mapperMethodAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        ActionContext<MapperLogWithBLOBs> actionContext = ActionContext.<MapperLogWithBLOBs>builder()
                .invokerName(MAPPER_INVOKER)
                .joinPoint(joinPoint)
                .build();
        //使用dao方法对应的invoker执行一系列command
        invoker.invoke(actionContext);
        if (actionContext.getMethodException() != null){
            throw actionContext.getMethodException();
        }
        return actionContext.getMethodResult();
    }
}
