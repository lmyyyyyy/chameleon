package cn.code.chameleon.selector;

import org.junit.Test;

/**
 * @author liumingyu
 * @create 2018-04-17 下午1:56
 */
public class RegexSelectorTest {

    @Test(expected = IllegalArgumentException.class)
    public void test() throws Exception {
        String regex = "\\d+(";
        new RegexSelector(regex);
    }

    @Test
    public void test_select() throws Exception {
        String regex = "\\(.+";
        String hello = "(hello world";
        RegexSelector regexSelector = new RegexSelector(regex);
        String reslt = regexSelector.select(hello);
        System.out.println(reslt);
    }

    @Test
    public void test_select_regex() throws Exception {
        String regex = "^.*(?=\\?)(?!\\?yy)";
        String source = "hello world?xx?yy";
        RegexSelector regexSelector = new RegexSelector(regex);
        String select = regexSelector.select(source);
        System.out.println(select);

        regex = "\\d{3}(?!\\d)";
        source = "123456asdf";
        regexSelector = new RegexSelector(regex);
        select = regexSelector.select(source);
        System.out.println(select);
    }
}
