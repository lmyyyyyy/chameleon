package cn.code.chameleon.monitor.actions.invoker.impl;


import cn.code.chameleon.monitor.actions.invoker.AbstractInvoker;
import cn.code.chameleon.monitor.pojo.ServiceLogWithBLOBs;
import org.springframework.stereotype.Component;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.SERVICE_INVOKER;

/**
 * service方法对应的invoker，其所需执行的command顺序如下 :
 * 0 ： UserInfoCommand，MethodParameterCommand;
 * 1 ： ServiceLogGenerateCommand
 * 5 ： MethodInvokerCommand
 * ORDER_LAST-1 ：ServiceLogFillingCommand
 * ORDER_LAST ： ServiceLogToDBCommand，PrintLogCommand
 * ORDER_FINALLY ：ErrorHandlerCommand
 *
 * @author liumingyu
 * @create 2018-01-20 下午5:03
 */
@Component(SERVICE_INVOKER)
public class ServiceInvoker extends AbstractInvoker<ServiceLogWithBLOBs> {
    @Override
    public String getInvokerName() {
        return SERVICE_INVOKER;
    }
}
