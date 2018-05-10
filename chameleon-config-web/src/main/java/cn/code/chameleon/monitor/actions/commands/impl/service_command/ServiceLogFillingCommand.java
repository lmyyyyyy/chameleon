package cn.code.chameleon.monitor.actions.commands.impl.service_command;

import cn.code.chameleon.monitor.actions.commands.AbstractCommand;
import cn.code.chameleon.monitor.actions.context.ActionContext;
import cn.code.chameleon.monitor.actions.exception.EntityIsNullException;
import cn.code.chameleon.monitor.annotation.Command;
import cn.code.chameleon.monitor.aspect.ServiceAspect;
import cn.code.chameleon.monitor.enums.InvokeStatusEnum;
import cn.code.chameleon.monitor.pojo.ServiceLogWithBLOBs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.SERVICE_INVOKER;
import static cn.code.chameleon.monitor.annotation.Command.ORDER_LAST;
import static cn.code.chameleon.monitor.util.ObjectEncodeUtil.encodeErrorMessage;
import static cn.code.chameleon.monitor.util.ObjectEncodeUtil.encodeObject;

/**
 * 填充字段
 */
@Component("ServiceLogFillingCommand")
@Command(order = ORDER_LAST - 1, invoker = {SERVICE_INVOKER})
public class ServiceLogFillingCommand extends AbstractCommand<ServiceLogWithBLOBs> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    @Override
    public void execute() {
        LOGGER.info("【ServiceLogFillingCommand执行命令】Service日志数据填充");
        ActionContext<ServiceLogWithBLOBs> actionContext = getActionContext();
        if (actionContext.getEntity() == null) {
            throw new EntityIsNullException("RunServiceLogWithBLOBs对象为空");
        }
        ServiceLogWithBLOBs entity = actionContext.getEntity();
        entity.setTimeCost(actionContext.getMethodEndTime() - actionContext.getMethodStartTime());
        if (actionContext.getMethodException() == null) {
            entity.setInvokeStatus(InvokeStatusEnum.SUCCESS.getCode());
            try {
                entity.setReturnValue(encodeObject(actionContext.getMethodResult()));
            } catch (Throwable t) {
                LOGGER.error("method result encode error : ", t);
            }
        } else {
            entity.setInvokeStatus(InvokeStatusEnum.FAIL.getCode());
            entity.setErrorMessage(encodeErrorMessage(actionContext.getMethodException()));
        }
        entity.setUpdateTime(new Date());
    }
}
