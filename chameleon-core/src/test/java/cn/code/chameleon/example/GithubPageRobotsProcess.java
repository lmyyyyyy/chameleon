package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.monitor.SpiderMonitor;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.scheduler.FileCachQueueSchduler;
import cn.code.chameleon.scheduler.QueueScheduler;

/**
 * @author liumingyu
 * @create 2018-04-17 下午6:25
 */
public class GithubPageRobotsProcess implements PageProcessor {

    private Site site = Site.init().setRetryTimes(3).setSleepTime(3000).setTimeOut(10000).setObeyRobots(true);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+/[\\w\\-]+/[\\w\\-]+)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+/tree/[\\w\\-]+)").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*")).toString();
        page.putField("name", page.getHtml().xpath("//h1[@class='public']/strong/a/text()").toString());
        if (page.getResults().get("name") == null) {
            page.setJump(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/article/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubPageRobotsProcess()).addUrls("https://github.com/lmyyyyyy/chameleon/tree/master").setScheduler(new QueueScheduler()).addSpiderListener(new SpiderMonitor()).setIgnoreUA(true).thread(5).run();
    }
}
