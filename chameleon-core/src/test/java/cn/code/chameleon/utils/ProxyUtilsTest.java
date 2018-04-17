package cn.code.chameleon.utils;

import cn.code.chameleon.proxy.Proxy;
import org.junit.Test;

/**
 * @author liumingyu
 * @create 2018-04-17 上午10:56
 */
public class ProxyUtilsTest {

    @Test
    public void test_proxy_validate() throws Exception {
        Proxy proxy = new Proxy("123.53.119.69", 61234);
        ProxyUtils.validateProxy(proxy);
    }
}
