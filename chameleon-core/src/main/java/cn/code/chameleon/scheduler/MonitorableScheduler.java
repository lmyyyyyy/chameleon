package cn.code.chameleon.scheduler;

import cn.code.chameleon.carrier.Task;

/**
 * @author liumingyu
 * @create 2018-04-11 下午3:38
 */
public interface MonitorableScheduler extends Scheduler {

    int getLeftRequestsCount(Task task);

    int getTotalRequestsCount(Task task);
}
