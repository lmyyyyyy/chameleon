package cn.code.chameleon.selector;

import org.jsoup.nodes.Element;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-11 下午3:31
 */
public class HtmlNode extends AbstractSelectable {

    private final List<Element> elements;

    public HtmlNode(List<Element> elements) {
        this.elements = elements;
    }

    public HtmlNode() {
        this.elements = null;
    }

    protected List<Element> getElements() {
        return elements;
    }
}
