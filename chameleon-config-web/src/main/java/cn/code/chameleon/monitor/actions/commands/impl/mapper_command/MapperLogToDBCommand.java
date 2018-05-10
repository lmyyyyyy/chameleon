package cn.code.chameleon.monitor.actions.commands.impl.mapper_command;

import cn.code.chameleon.monitor.actions.commands.AbstractCommand;
import cn.code.chameleon.monitor.annotation.Command;
import cn.code.chameleon.monitor.aspect.MapperAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.MAPPER_INVOKER;
import static cn.code.chameleon.monitor.annotation.Command.ORDER_LAST;

/**
 * mapper日志存入数据库
 */
@Command(order = ORDER_LAST, invoker = MAPPER_INVOKER)
public class MapperLogToDBCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapperAspect.class);

    @Override
    public void execute() {
        LOGGER.info("【MapperLogToDBCommand执行命令】Mapper日志入库");
    }
}
