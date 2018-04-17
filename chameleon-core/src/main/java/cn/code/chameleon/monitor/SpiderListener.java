package cn.code.chameleon.monitor;


import cn.code.chameleon.carrier.Request;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liumingyu
 * @create 2018-04-14 下午2:53
 */
public interface SpiderListener {

    void onSuccess(Request request);

    void onError(Request request);

    AtomicLong getErrorCount();

    AtomicLong getSuccessCount();

    Set<String> getErrorUrls();
}
