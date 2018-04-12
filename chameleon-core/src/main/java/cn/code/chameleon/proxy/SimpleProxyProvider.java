package cn.code.chameleon.proxy;

import cn.code.chameleon.carrier.Task;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liumingyu
 * @create 2018-04-11 下午9:08
 */
public class SimpleProxyProvider implements ProxyProvider {

    private List<Proxy> proxies;

    private AtomicInteger pointer;

    public SimpleProxyProvider(List<Proxy> proxies) {
        this(proxies, new AtomicInteger(-1));
    }

    public SimpleProxyProvider(List<Proxy> proxies, AtomicInteger pointer) {
        this.proxies = proxies;
        this.pointer = pointer;
    }

    public static SimpleProxyProvider store(Proxy... proxies) {
        List<Proxy> proxyTemp = Lists.newArrayListWithCapacity(proxies.length);
        for (Proxy proxy : proxies) {
            proxyTemp.add(proxy);
        }
        return new SimpleProxyProvider(Collections.unmodifiableList(proxyTemp));
    }

    @Override
    public Proxy getProxy(Task task) {
        return proxies.get(incrForLoop());
    }

    private int incrForLoop() {
        int index = pointer.incrementAndGet();
        int size = proxies.size();
        if (index < size) {
            return index;
        }
        while (!pointer.compareAndSet(index, index % size)) {
            index = pointer.get();
        }
        return index % size;
    }
}
