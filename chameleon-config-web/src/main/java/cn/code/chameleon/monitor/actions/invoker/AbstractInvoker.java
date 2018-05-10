package cn.code.chameleon.monitor.actions.invoker;

import cn.code.chameleon.monitor.actions.commands.Command;
import cn.code.chameleon.monitor.actions.commands.CommandExceptions;
import cn.code.chameleon.monitor.actions.context.ActionContext;
import cn.code.chameleon.monitor.actions.exception.InvokerNotFoundException;
import cn.code.chameleon.monitor.actions.exception.OrderLengthInvalidException;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-01-20 下午5:03
 */
public abstract class AbstractInvoker<C> implements Invoker<C>, InitializingBean, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractInvoker.class);
    private static final String LOG_PREFIX = "【执行命令】 ";

    private ApplicationContext applicationContext;

    @Getter
    private List<Command<C>> commands;

    /**
     * 执行命令
     *
     * @param actionContext
     */
    @Override
    public void invoke(ActionContext<C> actionContext) {
        LOGGER.info("{} Begin 调用者名称:{}" + LOG_PREFIX, actionContext.getInvokerName());
        for (Command<C> command : commands) {
            try {
                command.withContext(actionContext).execute();
            } catch (Throwable e) {
                withError(command, actionContext, e);
            }
        }
        LOGGER.info("{} End 执行了{}个命令", LOG_PREFIX, commands.size());
    }

    /**
     * 存储异常
     *
     * @param command
     * @param actionContext
     * @param e
     */
    private void withError(Command<C> command, ActionContext<C> actionContext, Throwable e) {
        LOGGER.info("{} {}存储异常error = {}", LOG_PREFIX, command, e.getMessage());
        if (actionContext != null && actionContext.getExceptionList() != null) {
            CommandExceptions exceptions = CommandExceptions.builder().command(command).throwable(e).build();
            actionContext.getExceptionList().add(exceptions);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        String name = getInvokerName();
        if (name == null || "".equals(name)) {
            throw new InvokerNotFoundException("Invoker未找到");
        }
        commands = this.getCommandsWithAnnotation(cn.code.chameleon.monitor.annotation.Command.class);
        //过滤不属于该invoker的command
        Iterator<Command<C>> iterator = commands.iterator();
        while (iterator.hasNext()) {
            Command command = iterator.next();
            if (!isCommandBelongToInvoker(command, name)) {
                iterator.remove();
            }
        }
        filterAndSortCommands(commands);
        LOGGER.info("{} {}注入了{}个命令", LOG_PREFIX, name, commands.size());
    }

    /**
     * 过滤排序命令
     *
     * @param commands
     */
    private void filterAndSortCommands(List<Command<C>> commands) {
        LOGGER.info("{} 过滤排序{}个命令", LOG_PREFIX, commands.size());
        Collections.sort(commands, new CommandOrderComparator<>());
    }

    /**
     * 命令是否属于执行者
     *
     * @param command
     * @param name
     * @return
     */
    private boolean isCommandBelongToInvoker(Command command, String name) {
        if (command == null) {
            return false;
        }
       cn.code.chameleon.monitor.annotation.Command annotation = command.getClass()
                .getAnnotation(cn.code.chameleon.monitor.annotation.Command.class);
        //若command注解的invoker数组包含当前invoker的名字，则该command属于当前invoker
        return annotation != null && ArrayUtils.indexOf(annotation.invoker(), name) >= 0;
    }

    /**
     * 根据注解获取所有命令
     *
     * @param annotationType
     * @return
     */
    private List<Command<C>> getCommandsWithAnnotation(Class<? extends Annotation> annotationType) {
        Map<String, Object> beansWithAnnotation = this.applicationContext.getBeansWithAnnotation(annotationType);
        if (MapUtils.isEmpty(beansWithAnnotation)) {
            return Lists.newArrayList();
        }
        List<Command<C>> beans = new ArrayList<>(beansWithAnnotation.size());
        for (Object instance : beansWithAnnotation.values()) {
            if (instance instanceof Command) {
                beans.add((Command<C>) instance);
            }
        }
        return beans;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 返回当前调用者的名称
     *
     * @return
     */
    public abstract String getInvokerName();

    /**
     * 自定义命令比较器
     *
     * @param <T>
     */
    private class CommandOrderComparator<T> implements Comparator<T> {
        @Override
        public int compare(T o1, T o2) {
            return Integer.compare(getOrder(o1), getOrder(o2));
        }

        private int getOrder(T o1) {
            if (o1 == null) {
                return Integer.MAX_VALUE;
            }
            cn.code.chameleon.monitor.annotation.Command annotation =
                    o1.getClass().getAnnotation(cn.code.chameleon.monitor.annotation.Command.class);
            if (annotation == null) {
                return Integer.MAX_VALUE;
            }
            int[] orderArray = annotation.order();
            //如果顺序数组中只有一个值，说明对所有的invoker，该command的顺序都一致
            if (orderArray.length == 1) {
                return orderArray[0];
            }
            if (orderArray.length != annotation.invoker().length) {
                throw new OrderLengthInvalidException("调用者顺序无法确定，请排查注解中相关参数");
            }
            int i = ArrayUtils.indexOf(annotation.invoker(), getInvokerName());
            if (i < 0) {
                return Integer.MAX_VALUE;
            }
            return annotation.order()[i];
        }
    }
}
