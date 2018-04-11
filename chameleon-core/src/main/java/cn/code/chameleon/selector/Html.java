package cn.code.chameleon.selector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author liumingyu
 * @create 2018-04-11 下午3:10
 */
public class Html extends HtmlNode implements Serializable {

    private static final long serialVersionUID = -1018706596423610244L;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static boolean DISABLE_HTML_ENTITY_ESCAPE;

    private Document document;

    public Html() {
    }

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

}
