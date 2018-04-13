package cn.code.chameleon.selector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-11 下午3:10
 */
public class Html extends HtmlNode implements Serializable {

    private static final long serialVersionUID = -1018706596423610244L;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static boolean DISABLE_HTML_ENTITY_ESCAPE;

    private Document document;

    public Html(String text, String url) {
        try {
            this.document = Jsoup.parse(text, url);
        } catch (Exception e) {
            document = null;
            LOGGER.warn("Jsoup parse document error: {}", e);
        }
    }

    public Html(String text) {
        try {
            this.document = Jsoup.parse(text);
        } catch (Exception e) {
            document = null;
            LOGGER.warn("Jsoup parse document error: {}", e);
        }
    }

    public Html(Document document) {
        this.document = document;
    }

    public static Html createHtml(String text) {
        return new Html(text);
    }

    public Document getDocument() {
        return document;
    }

    @Override
    public List<Element> getElements() {
        return Collections.<Element>singletonList(getDocument());
    }

    public String selectDocument(Selector selector) {
        if (selector instanceof ElementSelector) {
            ElementSelector elementSelector = (ElementSelector) selector;
            return elementSelector.select(getDocument());
        } else {
            return selector.select(getFirstSourceText());
        }
    }

    public List<String> selectDocumentForList(Selector selector) {
        if (selector instanceof ElementSelector) {
            ElementSelector elementSelector = (ElementSelector) selector;
            return elementSelector.selectList(getDocument());
        } else {
            return selector.selectList(getFirstSourceText());
        }
    }

}
