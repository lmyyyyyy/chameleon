package cn.code.chameleon.scheduler.component;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Task;

/**
 * @author liumingyu
 * @create 2018-04-11 下午4:31
 */
public interface DuplicateRemover {

    boolean isDuplicate(Request request, Task task);

    void resetDuplicate(Task task);

    int getDuplicateCounts(Task task);
}
