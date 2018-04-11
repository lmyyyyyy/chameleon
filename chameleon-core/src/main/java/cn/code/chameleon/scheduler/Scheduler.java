package cn.code.chameleon.scheduler;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Task;

/**
 * @author liumingyu
 * @create 2018-04-09 下午2:50
 */
public interface Scheduler {

    void push(Request request, Task task);

    Request poll(Task task);
}
