package cn.code.chameleon.monitor.actions.commands;


import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.monitor.actions.context.ActionContext;

/**
 * @author liumingyu
 * @create 2018-01-20 下午5:08
 */
public interface Command<T> {
    Command<T> withContext(ActionContext<T> actionContext) throws ChameleonException;

    void execute();
}
