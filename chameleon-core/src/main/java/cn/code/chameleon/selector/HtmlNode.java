package cn.code.chameleon.selector;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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

    @Override
    public Selectable select(Selector selector) {
        return selectList(selector);
    }

    @Override
    public Selectable selectList(Selector selector) {
        if (selector instanceof BaseElementSelector) {
            return selectElements((BaseElementSelector)selector);
        }
        return selectList(selector, getSourceTexts());
    }

    @Override
    protected List<String> getSourceTexts() {
        List<String> sourceTexts = new ArrayList<>();
        for (Element element : getElements()) {
            sourceTexts.add(element.toString());
        }
        return sourceTexts;
    }

    @Override
    public Selectable $(String selector) {
        CssSelector cssSelector = Selectors.$(selector);
        return selectElements(cssSelector);
    }

    @Override
    public Selectable $(String selector, String attrName) {
        CssSelector cssSelector = Selectors.$(selector, attrName);
        return selectElements(cssSelector);
    }

    @Override
    public Selectable links() {
        return selectElements(new LinksSelector());
    }

    private Selectable selectElements(BaseElementSelector elementSelector) {
        ListIterator<Element> elementIterator = getElements().listIterator();
        if (!elementSelector.hasAttribute()) {
            List<Element> resultElements = new ArrayList<>();
            while (elementIterator.hasNext()) {
                Element element = checkElementAndConvert(elementIterator);
                List<Element> elements = elementSelector.selectElements(element);
                resultElements.addAll(elements);
            }
            return new HtmlNode(resultElements);
        } else {
            List<String> results = new ArrayList<>();
            while (elementIterator.hasNext()) {
                Element element = checkElementAndConvert(elementIterator);
                List<String> result = elementSelector.selectList(element);
                results.addAll(result);
            }
            return new PlainText(results);
        }
    }

    private Element checkElementAndConvert(ListIterator<Element> elementListIterator) {
        Element element = elementListIterator.next();
        if (!(element instanceof Document)) {
            Document root = new Document(element.ownerDocument().baseUri());
            Element clone = element.clone();
            root.appendChild(clone);
            elementListIterator.set(root);
            return root;
        }
        return element;
    }

    @Override
    public Selectable smartContent() {
        SmartContentSelector smartContentSelector = Selectors.smartContent();
        return select(smartContentSelector, getSourceTexts());
    }

    @Override
    public Selectable xpath(String xpath) {
        XpathSelector xpathSelector = Selectors.xpath(xpath);
        return selectElements(xpathSelector);
    }

    @Override
    public List<Selectable> nodes() {
        List<Selectable> selectables = new ArrayList<>();
        for (Element element : getElements()) {
            List<Element> childElements = new ArrayList<>(1);
            childElements.add(element);
            selectables.add(new HtmlNode(childElements));
        }
        return selectables;
    }
}
