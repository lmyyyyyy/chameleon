package cn.code.chameleon.carrier;

import cn.code.chameleon.utils.HttpConstant;
import org.junit.Test;

/**
 * @author liumingyu
 * @create 2018-04-17 上午11:42
 */
public class RequestTest {

    @Test
    public void test_is_equal() throws Exception {
        Request request = new Request("www.lblogg.cn");
        Request request1 = new Request("www.lblogg.cn");
        System.out.println(request.equals(request1));
        request.setMethod(HttpConstant.Method.GET);
        request1.setMethod(HttpConstant.Method.POST);
        System.out.println(request.equals(request1));
    }
}
