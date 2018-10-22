package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.pipeline.ConsolePipeline;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.scheduler.FileCachQueueSchduler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-25 下午1:51
 */
public class ZhihuTopicPageProcessor implements PageProcessor {

    private Site site = Site.init().setTimeOut(10000).setSleepTime(3000).setCycleRetryTimes(3).setDomain("zhuanlan.zhihu.com");

    @Override
    public void process(Page page) {
        if (page.getRequest().getUrl().matches("http[s]?://www\\.zhihu\\.com/topic/[\\d]+[/\\w]*")) {
            page.setJump(true);
        }
        if (page.getRequest().getUrl().matches("http[s]?://www\\.zhihu\\.com/topics[#\\u4e00-\\u9fa5]*")) {
            page.setJump(true);
        }
        if (site.getDomain() != null) {
            List<String> temp = page.getHtml().links().regex("#[\\u4e00-\\u9fa5]+").all();
            List<String> topics = new ArrayList<>();
            for (String topic : temp) {
                if (topic == null || "".equals(topic)) {
                    continue;
                }
                topics.add("/topics" + topic);
            }
            page.addTargetRequests(topics);
        }
        page.addTargetRequests(page.getHtml().links().regex("/topic/[\\d]+").all());
        page.addTargetRequests(page.getHtml().links().regex("//zhuanlan\\.zhihu\\.com/p/[\\d]+").all());
        page.putField("title", page.getHtml().xpath("//h1[@class='Post-Title']/text()"));
        page.putField("author", page.getHtml().xpath("//div[@class='AuthorInfo-head']/span/div/div/a/text()"));
        page.putField("voteCount", page.getHtml().xpath("//span[@class='Voters']/button/text()"));
        page.putField("commentCount", page.getHtml().xpath("//div[@class='ContentItem-actions']/button[1]/text()"));
        List<String> temps;
        temps = page.getHtml().xpath("//div[@class='Popover']/div/text()").all();
        List<String> popovers = new ArrayList<>();
        for (String str : temps) {
            if (str == null || "".equals(str.trim())) {
                continue;
            }
            popovers.add(str);
        }
        page.putField("popover", popovers);
        page.putField("content", page.getHtml().xpath("//article[@class='Post-Main']/div/div[@class='RichText']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ZhihuTopicPageProcessor()).addPipeline(new ConsolePipeline()).addPipeline(new FilePipeline()).setScheduler(new FileCachQueueSchduler()).addUrls("https://www.zhihu.com/topics").thread(5).run();
    }
}
