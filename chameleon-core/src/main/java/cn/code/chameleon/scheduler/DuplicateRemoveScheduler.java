package cn.code.chameleon.scheduler;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.scheduler.component.DuplicateRemover;
import cn.code.chameleon.scheduler.component.HashSetDuplicateRemover;
import cn.code.chameleon.utils.HttpConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author liumingyu
 * @create 2018-04-11 下午3:42
 */
public abstract class DuplicateRemoveScheduler implements Scheduler {

    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    private DuplicateRemover duplicateRemover = new HashSetDuplicateRemover();

    public DuplicateRemover getDuplicateRemover() {
        return duplicateRemover;
    }

    public DuplicateRemoveScheduler setDuplicateRemover(DuplicateRemover duplicateRemover) {
        this.duplicateRemover = duplicateRemover;
        return this;
    }

    @Override
    public void push(Request request, Task task) {
        LOGGER.trace("[{}] get a candidate url: {}", new Date(), request.getUrl());
        if (shouldReserved(request) || noNeedDuplicateRemove(request) || !duplicateRemover.isDuplicate(request, task)) {
            LOGGER.debug("push the url to queue: {}" + request.getUrl());
            pushWhenNoDuplicate(request, task);
        }
    }

    public boolean shouldReserved(Request request) {
        return request.getExtra(Request.CYCLE_TRIED_TIMES) != null;
    }

    public boolean noNeedDuplicateRemove(Request request) {
        return HttpConstant.Method.POST.equalsIgnoreCase(request.getMethod());
    }

    protected abstract void pushWhenNoDuplicate(Request request, Task task);

}
