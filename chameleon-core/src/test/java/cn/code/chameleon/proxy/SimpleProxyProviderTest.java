package cn.code.chameleon.proxy;

import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.carrier.Task;
import org.junit.Test;

/**
 * @author liumingyu
 * @create 2018-04-14 下午6:39
 */
public class SimpleProxyProviderTest {

    private static Task task = Site.init().buildTask();

    @Test
    public void testGetProxy() throws Exception {
        Proxy proxy1 = new Proxy("127.0.0.1", 80);
        Proxy proxy2 = new Proxy("127.0.0.2", 81);

        SimpleProxyProvider simpleProxyProvider = SimpleProxyProvider.store(proxy1, proxy2);
        Proxy proxy = simpleProxyProvider.getProxy(task);
        System.out.println(proxy);
        proxy = simpleProxyProvider.getProxy(task);
        System.out.println(proxy);
        proxy = simpleProxyProvider.getProxy(task);
        System.out.println(proxy);
    }
}
