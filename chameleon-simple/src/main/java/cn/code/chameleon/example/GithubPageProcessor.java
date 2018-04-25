package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.monitor.SpiderMonitor;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.scheduler.FileCachQueueSchduler;

/**
 * @author liumingyu
 * @create 2018-04-17 下午6:25
 */
public class GithubPageProcessor implements PageProcessor {

    private Site site = Site.init().setRetryTimes(3).setSleepTime(3000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*")).toString();
        page.putField("name", page.getHtml().xpath("//h1[@class='public']/strong/a/text()").toString());
        if (page.getResults().get("name") == null) {
            page.setJump(true);
        }
        page.putField("fork", page.getHtml().xpath("//div[@id='js-repo-pjax-container']/div[1]/div/ul/li[3]/a[2]/text()"));
        page.putField("commits", page.getHtml().xpath("//li[@class='commits']/a/span/text()"));
        page.putField("branchs", page.getHtml().xpath("//ul[@class='numbers-summary']/li[2]/a/span/text()"));
        page.putField("releases", page.getHtml().xpath("//ul[@class='numbers-summary']/li[3]/a/span/text()"));
        page.putField("contributor", page.getHtml().xpath("//ul[@class='numbers-summary']/li[4]/a/span/text()"));
        page.putField("desc", page.getHtml().xpath("//div[@class='js-repo-meta-container']/div/div/span/text()"));
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/article/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubPageProcessor()).addUrls("https://github.com/lmyyyyyy/chameleon").addSpiderListener(new SpiderMonitor()).thread(5).run();
    }
}
