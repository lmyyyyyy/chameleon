package cn.code.chameleon.downloader;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.utils.UrlUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.junit.Test;

/**
 * @author liumingyu
 * @create 2018-04-16 下午5:45
 */
public class HttpUriRequestConvertTest {

    private HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();

    @Test
    public void test_convert() throws Exception {
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(new Request(UrlUtils.fixIllegalCharacterInUrl("http://bj.zhongkao.com/beikao/yimo/##")), Site.init(), null);
        HttpUriRequest httpUriRequest = requestContext.getHttpUriRequest();
        System.out.println(httpUriRequest.getURI());
        System.out.println(httpUriRequest.getMethod());
    }
}
