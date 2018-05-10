package cn.code.chameleon.monitor.actions.commands;

import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.monitor.actions.context.ActionContext;
import lombok.Getter;

/**
 * @author liumingyu
 * @create 2018-01-21 下午3:34
 */
public abstract class AbstractCommand<T> implements Command<T> {

    @Getter
    private ActionContext<T> actionContext;

    @Override
    public Command<T> withContext(ActionContext<T> actionContext) throws ChameleonException {
        if (actionContext == null) {
            throw new ChameleonException(ResultCodeEnum.ACTION_CONTEXT);
        }
        this.actionContext = actionContext;
        return this;
    }
}
