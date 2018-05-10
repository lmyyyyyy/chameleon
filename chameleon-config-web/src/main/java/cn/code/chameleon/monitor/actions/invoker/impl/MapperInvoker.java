package cn.code.chameleon.monitor.actions.invoker.impl;

import cn.code.chameleon.monitor.actions.invoker.AbstractInvoker;
import cn.code.chameleon.monitor.pojo.MapperLogWithBLOBs;
import org.springframework.stereotype.Component;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.MAPPER_INVOKER;

/**
 * mapper方法对应的invoker，其所需执行的command对应如下：
 * 0 : UserInfoCommand，MethodParameterCommand;
 * ORDER_START : MapperLogGenerateCommand
 * ORDER_START + 1 : RecordSQLCommand
 * 5 : MethodInvokerCommand
 * ORDER_LAST ： PrintLogCommand
 * ORDER_FINALLY ：ErrorHandlerCommand
 *
 * @author liumingyu
 * @create 2018-01-20 下午5:03
 */
@Component(MAPPER_INVOKER)
public class MapperInvoker extends AbstractInvoker<MapperLogWithBLOBs> {
    @Override
    public String getInvokerName() {
        return MAPPER_INVOKER;
    }
}
