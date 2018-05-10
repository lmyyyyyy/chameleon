package cn.code.chameleon.monitor.actions.context;

import cn.code.chameleon.monitor.actions.commands.CommandExceptions;
import lombok.Builder;
import lombok.Data;
import org.aspectj.lang.JoinPoint;

import java.util.LinkedList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-01-20 下午5:04
 */
@Data
@Builder
public class ActionContext<T> {
    /** 当前实体对象 */
    private T entity;

    /** 当前方法开始时间 **/
    private long methodStartTime;

    /** 方法结束时间 **/
    private long methodEndTime;

    /** 当前方法的aop joinpoint **/
    private JoinPoint joinPoint;

    /** 当前方法抛出的异常 **/
    private Throwable methodException;

    /** 方法执行的返回结果 **/
    private Object methodResult;

    /** 编码成string的方法参数 **/
    private String methodArgs;

    /** 当前命令调用者名称 **/
    private String invokerName;

    /** 当前执行方法的用户ID **/
    private Long operatorId;

    /** 当前执行方法的用户名 **/
    private String userName;

    /** 命令执行过程中抛出的各类异常 **/
    private List<CommandExceptions> exceptionList = new LinkedList<>();
}
