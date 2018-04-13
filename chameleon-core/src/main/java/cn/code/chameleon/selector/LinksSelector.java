package cn.code.chameleon.selector;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-13 下午2:39
 */
public class LinksSelector extends BaseElementSelector {

    @Override
    public Element selectElement(Element element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Element> selectElements(Element element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasAttribute() {
        return true;
    }

    @Override
    public String select(Element element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> selectList(Element element) {
        Elements elements = element.select("a");
        List<String> links = new ArrayList<>(elements.size());
        for (Element element0 : elements) {
            if (!StringUtils.isBlank(element0.baseUri())) {
                links.add(element0.attr("abs:href"));
            } else {
                links.add(element0.attr("href"));
            }
        }
        return links;
    }
}
