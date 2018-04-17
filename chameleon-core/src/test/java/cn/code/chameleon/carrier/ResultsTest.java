package cn.code.chameleon.carrier;

import org.junit.Test;

/**
 * @author liumingyu
 * @create 2018-04-17 上午11:50
 */
public class ResultsTest {

    @Test
    public void test_order() throws Exception {
        Results results = new Results();
        results.put("a", "a");
        results.put("c", "c");
        results.put("b", "b");
        System.out.println(results.getAll().keySet());
    }
}
