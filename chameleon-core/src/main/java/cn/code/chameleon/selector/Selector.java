package cn.code.chameleon.selector;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-10 下午6:03
 */
public interface Selector {

    String select(String text);

    List<String> selectList(String text);
}
