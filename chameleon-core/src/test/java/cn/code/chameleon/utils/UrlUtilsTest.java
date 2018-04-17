package cn.code.chameleon.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liumingyu
 * @create 2018-04-17 上午11:13
 */
public class UrlUtilsTest {

    @Test
    public void test_get_domain() throws Exception {
        String url = "http://www.lblogg.cn/single/124";
        Assert.assertEquals("www.lblogg.cn", UrlUtils.getDomain(url));
        url = "www.lblogg.cn/single/124";
        Assert.assertEquals("www.lblogg.cn", UrlUtils.getDomain(url));
        url = "http://www.lblogg.cn/";
        Assert.assertEquals("www.lblogg.cn", UrlUtils.getDomain(url));
    }

    @Test
    public void test_relative_url() throws Exception {
        String url = "http://www.lblogg.cn/single/124";
        String absUrl = UrlUtils.canonicalizeUrl("123", url);
        Assert.assertEquals(absUrl, "http://www.lblogg.cn/single/123");
        absUrl = UrlUtils.canonicalizeUrl("../123", url);
        Assert.assertEquals(absUrl, "http://www.lblogg.cn/123");
        url = "http://www.lblogg.cn/single/124/";
        absUrl = UrlUtils.canonicalizeUrl("../../123", url);
        Assert.assertEquals(absUrl, "http://www.lblogg.cn/123");
    }
}
