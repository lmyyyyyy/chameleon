package cn.code.chameleon.scheduler.component;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Task;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liumingyu
 * @create 2018-04-11 下午4:37
 */
public class HashSetDuplicateRemover implements DuplicateRemover {

    private Set<String> urls = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    @Override
    public boolean isDuplicate(Request request, Task task) {
        return !urls.add(getUrl(request));
    }

    public String getUrl(Request request) {
        if (request == null) {
            return null;
        }
        return request.getUrl();
    }

    @Override
    public void resetDuplicate(Task task) {
        urls.clear();
    }

    @Override
    public int getDuplicateCounts(Task task) {
        return urls.size();
    }
}
