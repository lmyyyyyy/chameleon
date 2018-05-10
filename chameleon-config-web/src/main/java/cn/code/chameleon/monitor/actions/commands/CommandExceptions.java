package cn.code.chameleon.monitor.actions.commands;

import lombok.Builder;
import lombok.Data;

/**
 * 命令执行过程中出现的异常记录
 * @author liumingyu
 * @create 2018-01-20 下午5:04
 */
@Data
@Builder
public class CommandExceptions {
    private Throwable throwable;
    private Command command;
}
