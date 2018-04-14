package cn.code.chameleon.monitor;


import cn.code.chameleon.carrier.Request;

/**
 * @author liumingyu
 * @create 2018-04-14 下午2:53
 */
public interface SpiderListener {

    void onSuccess(Request request);

    void onError(Request request);
}
