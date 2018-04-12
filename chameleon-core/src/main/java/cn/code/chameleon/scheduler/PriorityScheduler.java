package cn.code.chameleon.scheduler;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.utils.CompareUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author liumingyu
 * @create 2018-04-09 下午5:15
 */
public class PriorityScheduler extends DuplicateRemoveScheduler implements MonitorableScheduler {

    public static final int INIT_CAPACITY = 8;

    private BlockingQueue<Request> noPriorityQueue = new LinkedBlockingQueue<>();

    private PriorityBlockingQueue<Request> maxPriorityQueue = new PriorityBlockingQueue<>(INIT_CAPACITY, (request1, request2) ->
            -CompareUtils.compareLong(request1.getPriority(), request2.getPriority())
    );

    private PriorityBlockingQueue<Request> minPriorityQueue = new PriorityBlockingQueue<>(INIT_CAPACITY, (request1, request2) ->
            -CompareUtils.compareLong(request1.getPriority(), request2.getPriority())
    );

    @Override
    protected void pushWhenNoDuplicate(Request request, Task task) {
        if (request.getPriority() == 0) {
            noPriorityQueue.add(request);
        } else if (request.getPriority() > 0) {
            maxPriorityQueue.add(request);
        } else {
            minPriorityQueue.add(request);
        }
    }

    @Override
    public synchronized Request poll(Task task) {
        Request request = maxPriorityQueue.poll();
        if (request != null) {
            return request;
        }
        request = noPriorityQueue.poll();
        if (request != null) {
            return request;
        }
        return minPriorityQueue.poll();
    }

    @Override
    public int getLeftRequestsCount(Task task) {
        return noPriorityQueue.size();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return getDuplicateRemover().getDuplicateCounts(task);
    }
}
