package cn.code.chameleon.processor;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-12 上午10:56
 */
public class SimplePageProcessor implements PageProcessor {

    private String urlPattern;

    private Site site;

    public SimplePageProcessor(String urlPattern) {
        this.site = Site.init();
        this.urlPattern = "(" + urlPattern.replace(".", "\\.").replace("*", "[^\"'#]*") + ")";
    }

    @Override
    public void process(Page page) {
        List<String> requests = page.getHtml().links().regex(urlPattern).all();
        page.addTargetRequests(requests);
        page.putField("title", page.getHtml().xpath("//title"));
        page.putField("content", page.getHtml().smartContent());
        page.putField("html", page.getHtml().toString());

    }

    @Override
    public Site getSite() {
        return site;
    }
}
