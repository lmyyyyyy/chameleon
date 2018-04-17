package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.monitor.SpiderMonitor;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.processor.PageProcessor;

/**
 * @author liumingyu
 * @create 2018-04-17 下午7:42
 */
public class ZhihuPageProcess implements PageProcessor {

    private Site site = Site.init().setTimeOut(10000).setRetryTimes(3).setRetryTimes(300).setCycleRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("https://www\\.zhihu\\.com/question/\\d+/answer/\\d+.*").all());
        page.putField("title",page.getHtml().xpath("//h1[@class='QuestionHeader-title']/text()").toString());
        page.putField("question", page.getHtml().xpath("//div[@class='QuestionRichText']/text()").toString());
        page.putField("answer", page.getHtml().xpath("//div[@class='QuestionAnswer-content']/tidyText()").toString());
        if (page.getResults().get("title") == null) {
            page.setJump(true);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ZhihuPageProcess()).thread(10).addPipeline(new FilePipeline()).addUrls("https://www.zhihu.com/").addSpiderListener(new SpiderMonitor()).run();

    }
}
