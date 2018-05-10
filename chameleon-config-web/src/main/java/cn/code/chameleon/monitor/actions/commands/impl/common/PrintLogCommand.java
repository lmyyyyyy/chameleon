package cn.code.chameleon.monitor.actions.commands.impl.common;


import cn.code.chameleon.monitor.actions.commands.AbstractCommand;
import cn.code.chameleon.monitor.actions.context.ActionContext;
import cn.code.chameleon.monitor.annotation.Command;
import cn.code.chameleon.monitor.aspect.ServiceAspect;
import cn.code.chameleon.service.RedisClient;
import cn.code.chameleon.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.MAPPER_INVOKER;
import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.SERVICE_INVOKER;
import static cn.code.chameleon.monitor.annotation.Command.ORDER_LAST;


/**
 * 输出日志
 *
 * @author liumingyu
 * @create 2018-01-21 下午5:10
 */
@Component("PrintLogCommand")
@Command(order = ORDER_LAST, invoker = {SERVICE_INVOKER, MAPPER_INVOKER})
public class PrintLogCommand extends AbstractCommand<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    @Autowired
    private RedisClient redisClient;

    @Override
    public void execute() {
        LOGGER.info("【PrintLogCommand执行命令】输出日志");
        Boolean flag = Constants.METHOD_LOG_PRINT_SWITCH;
        String temp = redisClient.get(Constants.METHOD_LOG_PRINT_SWITCH_KEY);
        if (temp != null && !"".equals(temp)) {
            if (!flag.toString().equals(temp)) {
                flag = !flag;
            }
        }
        if (flag) {
            ActionContext actionContext = getActionContext();
            LOGGER.info("[MethodLog]- : result[{}] , error[{}], methodCost [{}], logEntity [{}]",
                    new Object[]{
                            actionContext.getMethodResult(), actionContext.getMethodException(),
                            actionContext.getMethodEndTime() - actionContext.getMethodStartTime(), actionContext.getEntity()
                    }
            );
        }
    }
}
