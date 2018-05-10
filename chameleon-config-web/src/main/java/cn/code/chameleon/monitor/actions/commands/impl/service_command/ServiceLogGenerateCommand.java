package cn.code.chameleon.monitor.actions.commands.impl.service_command;

import cn.code.chameleon.monitor.actions.commands.AbstractCommand;
import cn.code.chameleon.monitor.actions.context.ActionContext;
import cn.code.chameleon.monitor.annotation.Command;
import cn.code.chameleon.monitor.annotation.ServiceMethod;
import cn.code.chameleon.monitor.aspect.ServiceAspect;
import cn.code.chameleon.monitor.enums.InvokeStatusEnum;
import cn.code.chameleon.monitor.pojo.ServiceLogWithBLOBs;
import cn.code.chameleon.monitor.util.AnnotationUtil;
import cn.code.chameleon.monitor.util.LogUtil;
import cn.code.chameleon.utils.Constants;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.SERVICE_INVOKER;
import static cn.code.chameleon.monitor.annotation.Command.ORDER_START;
import static cn.code.chameleon.monitor.util.AnnotationUtil.getServiceClassAnnotation;
import static cn.code.chameleon.monitor.util.AnnotationUtil.getServiceMethodAnnotation;

/**
 * 生成service日志
 */
@Component("ServiceLogGenerateCommand")
@Command(order = ORDER_START, invoker = {SERVICE_INVOKER})
public class ServiceLogGenerateCommand extends AbstractCommand<ServiceLogWithBLOBs> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    @Override
    public void execute() {
        LOGGER.info("【ServiceLogGenerateCommand执行命令】Service日志生成实体");
        ActionContext<ServiceLogWithBLOBs> actionContext = getActionContext();
        ServiceLogWithBLOBs runServiceLog = new ServiceLogWithBLOBs();
        JoinPoint joinPoint = actionContext.getJoinPoint();
        String methodName = actionContext.getJoinPoint().getSignature().toString();
        runServiceLog.setClassName(getModuleName(joinPoint));
        runServiceLog.setMethodName(methodName);
        runServiceLog.setMethodParam(actionContext.getMethodArgs());
        runServiceLog.setOperatorId(actionContext.getOperatorId());
        runServiceLog.setOperatorName(actionContext.getUserName());
        runServiceLog.setReturnValue("");
        runServiceLog.setTimeCost(0L);
        runServiceLog.setInvokeStatus(InvokeStatusEnum.PROCEEDING.getCode());
        Date date = new Date();
        runServiceLog.setAddTime(date);
        runServiceLog.setUpdateTime(date);
        pushCurrentOpLog(runServiceLog);
        actionContext.setEntity(runServiceLog);
    }

    private String getModuleName(JoinPoint joinPoint) {
        //先获取方法上的注解
        ServiceMethod annotation = getServiceMethodAnnotation(joinPoint);
        //再获取接口或者类上的接口
        if (annotation == null) {
            annotation = getServiceClassAnnotation(joinPoint);
        }

        if (annotation != null) {
            return annotation.module();
        }

        return AnnotationUtil.getServiceClass(joinPoint).getName();
    }

    private void pushCurrentOpLog(ServiceLogWithBLOBs runServiceLogWithBLOBs) {
        if (LogUtil.isServiceLogStackEmpty() || Constants.SERVICE_LOG_RECURSION_SWITCH) {
            LogUtil.pushCurrentServiceLog(runServiceLogWithBLOBs);
        }
    }
}
