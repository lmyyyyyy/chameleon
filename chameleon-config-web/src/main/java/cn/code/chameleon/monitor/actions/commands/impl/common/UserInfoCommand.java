package cn.code.chameleon.monitor.actions.commands.impl.common;

import cn.code.chameleon.monitor.actions.commands.AbstractCommand;
import cn.code.chameleon.monitor.actions.context.ActionContext;
import cn.code.chameleon.monitor.annotation.Command;
import cn.code.chameleon.monitor.aspect.ServiceAspect;
import cn.code.chameleon.pojo.User;
import cn.code.chameleon.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.MAPPER_INVOKER;
import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.SERVICE_INVOKER;
import static cn.code.chameleon.monitor.annotation.Command.ORDER_INIT;

/**
 * 记录用户信息
 *
 * @author liumingyu
 * @create 2018-01-21 下午5:15
 */
@Component("UserInfoCommand")
@Command(order = ORDER_INIT, invoker = {SERVICE_INVOKER, MAPPER_INVOKER})
public class UserInfoCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    @Override
    public void execute() {
        LOGGER.info("【UserInfoCommand执行命令】获取当前线程用户信息");
        ActionContext actionContext = getActionContext();
        User user = null;
        try {
            user = RequestUtil.getCurrentUser();
        } catch (Exception e) {
            LOGGER.error("【监控模块】 获取当前用户信息异常", e);
        }
        if (user != null) {
            actionContext.setUserName(user.getName() == null ? "系统" : user.getName());
            actionContext.setOperatorId(user.getId() == null ? 0 : user.getId());
        } else {
            actionContext.setUserName("系统");
            actionContext.setOperatorId(0L);
        }
    }
}
