package cn.code.chameleon.monitor.actions.commands.impl.mapper_command;

import cn.code.chameleon.monitor.actions.commands.AbstractCommand;
import cn.code.chameleon.monitor.actions.context.ActionContext;
import cn.code.chameleon.monitor.actions.exception.EntityIsNullException;
import cn.code.chameleon.monitor.annotation.Command;
import cn.code.chameleon.monitor.aspect.MapperAspect;
import cn.code.chameleon.monitor.enums.InvokeStatusEnum;
import cn.code.chameleon.monitor.pojo.MapperLogWithBLOBs;
import cn.code.chameleon.utils.RegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.MAPPER_INVOKER;
import static cn.code.chameleon.monitor.annotation.Command.ORDER_LAST;
import static cn.code.chameleon.monitor.util.ObjectEncodeUtil.encodeErrorMessage;

/**
 * 填充字段
 */
@Component("MapperLogFillingCommand")
@Command(order = ORDER_LAST - 1, invoker = {MAPPER_INVOKER})
public class MapperLogFillingCommand extends AbstractCommand<MapperLogWithBLOBs> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperAspect.class);

    @Override
    public void execute() {
        LOGGER.info("【MapperLogFillingCommand】Mapper日志数据填充");
        ActionContext<MapperLogWithBLOBs> actionContext = getActionContext();
        if (actionContext.getEntity() == null) {
            throw new EntityIsNullException("RunMapperLogWithBLOBs对象为空");
        }
        MapperLogWithBLOBs entity = actionContext.getEntity();
        entity.setTimeCost(actionContext.getMethodEndTime() - actionContext.getMethodStartTime());
        if (actionContext.getMethodException() == null) {
            entity.setInvokeStatus((byte) InvokeStatusEnum.SUCCESS.getCode());
            try {
                if (actionContext.getMethodResult() instanceof Integer || RegexUtils.checkDigit(actionContext.getMethodResult().toString())) {
                    entity.setInfluenceRow((Integer)actionContext.getMethodResult());
                } else {
                    entity.setInfluenceRow(0);
                }
            } catch (Throwable t) {
                LOGGER.error("method result encode error : ", t);
            }
        } else {
            entity.setInvokeStatus((byte) InvokeStatusEnum.FAIL.getCode());
            entity.setErrorMessage(encodeErrorMessage(actionContext.getMethodException()));
        }
        entity.setUpdateTime(new Date());
    }
}
