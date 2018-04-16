package cn.code.chameleon.proxy;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-14 下午6:12
 */
public class ProxyTest {

    private static volatile List<Proxy> httpProxyList = new ArrayList<>();

    @BeforeClass
    public static void init() throws Exception {
        Proxy proxy = new Proxy("127.0.0.1", 80);
        Proxy proxy1 = new Proxy("127.0.0.2", 81);
        Proxy proxy2 = new Proxy("127.0.0.3", 82, "liumingyu", "123");
        Proxy proxy3 = new Proxy("127.0.0.4", 83, "heiheihei", "123");
        httpProxyList.add(proxy);
        httpProxyList.add(proxy1);
        httpProxyList.add(proxy2);
        httpProxyList.add(proxy3);
    }

    @Test
    public void test() {
        for (int i = 0; i < httpProxyList.size(); i++) {
            System.out.println(httpProxyList.get(i));
        }
    }
}
