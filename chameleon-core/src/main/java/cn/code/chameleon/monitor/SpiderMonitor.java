package cn.code.chameleon.monitor;


import cn.code.chameleon.carrier.Request;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liumingyu
 * @create 2018-04-14 下午3:09
 */
public abstract class SpiderMonitor implements SpiderListener {

    private final AtomicLong successCount = new AtomicLong(0);

    private final AtomicLong errorCount = new AtomicLong(0);

    private Set<String> errorUrls = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void onSuccess(Request request) {
        successCount.incrementAndGet();
    }

    @Override
    public void onError(Request request) {
        errorUrls.add(request.getUrl());
        errorCount.incrementAndGet();
    }

    public AtomicLong getErrorCount() {
        return errorCount;
    }

    public AtomicLong getSuccessCount() {
        return successCount;
    }

    public Set<String> getErrorUrls() {
        return errorUrls;
    }
}
