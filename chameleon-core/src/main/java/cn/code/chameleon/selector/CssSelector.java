package cn.code.chameleon.selector;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-13 下午2:58
 */
public class CssSelector extends BaseElementSelector {

    private String selectorText;

    private String attrName;

    public CssSelector(String selectorText) {
        this.selectorText = selectorText;
    }

    public CssSelector(String selectorText, String attrName) {
        this.selectorText = selectorText;
        this.attrName = attrName;
    }

    private String getValue(Element element) {
        if (attrName == null) {
            return element.outerHtml();
        } else if ("innerHtml".equalsIgnoreCase(attrName)) {
            return element.html();
        } else if ("text".equalsIgnoreCase(attrName)) {
            return getText(element);
        } else if ("allText".equalsIgnoreCase(attrName)) {
            return element.text();
        } else {
            return element.attr(attrName);
        }
    }

    protected String getText(Element element) {
        StringBuilder builder = new StringBuilder();
        for (Node node : element.childNodes()) {
            if (node instanceof TextNode) {
                TextNode textNode = (TextNode) node;
                builder.append(textNode.text());
            }
        }
        return builder.toString();
    }

    @Override
    public Element selectElement(Element element) {
        Elements elements = element.select(selectorText);
        if (CollectionUtils.isNotEmpty(elements)) {
            return elements.get(0);
        }
        return null;
    }

    @Override
    public List<Element> selectElements(Element element) {
        return element.select(selectorText);
    }

    @Override
    public boolean hasAttribute() {
        return attrName != null;
    }

    @Override
    public String select(Element element) {
        List<Element> elements = selectElements(element);
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }
        return getValue(elements.get(0));
    }

    @Override
    public List<String> selectList(Element element) {
        List<String> results = new ArrayList<>();
        List<Element> elements = selectElements(element);
        if (CollectionUtils.isEmpty(elements)) {
            return results;
        }
        for (Element element0 : elements) {
            String value = getValue(element0);
            if (value != null) {
                results.add(value);
            }
        }
        return results;
    }
}
