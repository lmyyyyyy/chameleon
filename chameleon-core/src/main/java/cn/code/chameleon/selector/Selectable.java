package cn.code.chameleon.selector;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-11 下午3:07
 */
public interface Selectable {

    Selectable $(String selector);

    Selectable $(String selector, String attrName);

    Selectable css(String selector);

    Selectable css(String selector, String attrName);

    Selectable links();

    Selectable smartContent();

    Selectable regex(String regex);

    Selectable regex(String regex, int group);

    Selectable replace(String regex, String replacement);

    Selectable jsonPath(String jsonPath);

    Selectable select(Selector selector);

    Selectable selectList(Selector selector);

    List<Selectable> nodes();

    String toString();

    String get();

    boolean isMatch();

    List<String> all();

}
