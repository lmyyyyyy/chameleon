package cn.code.chameleon.spider.processor;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.model.SpiderTemplate;
import cn.code.chameleon.processor.PageProcessor;

/**
 * @author liumingyu
 * @create 2018-05-12 下午9:06
 */
public class CommonPageProcessor implements PageProcessor {

    private Site site;

    public CommonPageProcessor(SpiderTemplate spiderTemplate) {
        site = Site.init();
    }

    @Override
    public void process(Page page) {
    }

    @Override
    public Site getSite() {
        return null;
    }
}
