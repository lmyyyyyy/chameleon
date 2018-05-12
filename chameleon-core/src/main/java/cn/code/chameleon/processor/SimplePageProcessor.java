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

    public SimplePageProcessor(String urlPattern, Site site) {
        this.site = site;
        this.urlPattern = urlPattern;
    }

    @Override
    public void process(Page page) {
        List<String> requests = page.getHtml().links().regex(urlPattern).all();
        page.addTargetRequests(requests);
        String title = page.getHtml().xpath("//title").get();
        if (title == null || "".equals(title)) {
            page.setJump(true);
        }
        page.putField("title", title);
        page.putField("content", page.getHtml().smartContent());
        page.putField("html", page.getHtml().toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public SimplePageProcessor setSite(Site site) {
        this.site = site;
        return this;
    }
}
