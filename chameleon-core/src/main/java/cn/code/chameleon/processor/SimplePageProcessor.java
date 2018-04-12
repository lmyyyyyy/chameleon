package cn.code.chameleon.processor;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;

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

    }

    @Override
    public Site getSite() {
        return site;
    }
}
