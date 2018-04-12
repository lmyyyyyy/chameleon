package cn.code.chameleon.scheduler;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author liumingyu
 * @create 2018-04-09 下午5:14
 */
public class QueueScheduler extends DuplicateRemoveScheduler implements MonitorableScheduler {

    private BlockingQueue<Request> requests = new LinkedBlockingQueue<>();

    @Override
    public Request poll(Task task) {
        return requests.poll();
    }

    @Override
    public void pushWhenNoDuplicate(Request request, Task task) {
        requests.add(request);
    }

    @Override
    public int getLeftRequestsCount(Task task) {
        return requests.size();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return getDuplicateRemover().getDuplicateCounts(task);
    }
}
