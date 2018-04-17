package cn.code.chameleon.selector;

import org.junit.Test;

/**
 * @author liumingyu
 * @create 2018-04-17 下午2:56
 */
public class JsonTest {

    private String text = "callback({\"name\":\"json)\"})";

    @Test
    public void test_remove_padding() throws Exception {
        String name = new Json(text).removePadding("callback").jsonPath("$.name").get();
        System.out.println(name);
    }
}
