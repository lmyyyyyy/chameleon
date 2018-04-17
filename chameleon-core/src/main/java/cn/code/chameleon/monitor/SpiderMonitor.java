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
public class SpiderMonitor implements SpiderListener {

    private final AtomicLong successCount = new AtomicLong(0);

    private final AtomicLong errorCount = new AtomicLong(0);

    private Set<String> errorUrls = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void onSuccess(Request request) {
        successCount.incrementAndGet();
        //System.out.println("成功爬取 : " + getSuccessCount());
    }

    @Override
    public void onError(Request request) {
        errorUrls.add(request.getUrl());
        errorCount.incrementAndGet();
        //System.out.println("失败爬取 : " + getErrorCount());
    }

    @Override
    public AtomicLong getErrorCount() {
        return errorCount;
    }

    @Override
    public AtomicLong getSuccessCount() {
        return successCount;
    }

    @Override
    public Set<String> getErrorUrls() {
        return errorUrls;
    }
}
