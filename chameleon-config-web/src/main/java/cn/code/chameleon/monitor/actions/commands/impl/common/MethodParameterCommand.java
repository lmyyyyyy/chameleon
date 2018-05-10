package cn.code.chameleon.monitor.actions.commands.impl.common;


import cn.code.chameleon.monitor.actions.commands.AbstractCommand;
import cn.code.chameleon.monitor.actions.context.ActionContext;
import cn.code.chameleon.monitor.annotation.Command;
import cn.code.chameleon.monitor.aspect.ServiceAspect;
import cn.code.chameleon.monitor.util.ObjectEncodeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.MAPPER_INVOKER;
import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.SERVICE_INVOKER;
import static cn.code.chameleon.monitor.annotation.Command.ORDER_INIT;

/**
 * 记录方法参数
 *
 * @author liumingyu
 * @create 2018-01-21 下午5:00
 */
@Component("RecordMethodParameterCommand")
@Command(order = ORDER_INIT, invoker = {SERVICE_INVOKER, MAPPER_INVOKER})
public class MethodParameterCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    @Override
    public void execute() {
        LOGGER.info("【MethodParameterCommand执行命令】设置方法参数");
        ActionContext actionContext = getActionContext();
        try {
            actionContext.setMethodArgs(ObjectEncodeUtil.encodeMethodArgs(actionContext.getJoinPoint().getArgs()));
        } catch (JsonProcessingException e) {
            LOGGER.error("[MethodLog]- {} 方法参数编码出错 : {}", actionContext.getInvokerName(), e);
            actionContext.setMethodArgs("");
        }
    }
}
