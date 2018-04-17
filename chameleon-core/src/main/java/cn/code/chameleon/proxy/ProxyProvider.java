package cn.code.chameleon.proxy;

import cn.code.chameleon.carrier.Task;

/**
 * @author liumingyu
 * @create 2018-04-09 下午5:21
 */
public interface ProxyProvider {

    Proxy getProxy(Task task);

}
