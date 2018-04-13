package cn.code.chameleon.selector;

import org.jsoup.nodes.Element;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-13 下午2:40
 */
public interface ElementSelector {

    String select(Element element);

    List<String> selectList(Element element);
}
