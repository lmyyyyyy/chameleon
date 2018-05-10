package cn.code.chameleon.monitor.actions.invoker;


import cn.code.chameleon.monitor.actions.context.ActionContext;

/**
 * @author liumingyu
 * @create 2018-01-20 下午5:02
 */
public interface Invoker<T> {
    void invoke(ActionContext<T> actionContext);
}
