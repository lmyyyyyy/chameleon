package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.pipeline.ConsolePipeline;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.scheduler.FileCachQueueSchduler;

/**
 * @author liumingyu
 * @create 2018-05-02 上午10:22
 */
public class BiQuGePageProcessor implements PageProcessor {

    private Site site = Site.init().setTimeOut(10000).setSleepTime(3000).setCycleRetryTimes(3);

    @Override
    public void process(Page page) {
        if (page.getRequest().getUrl().matches("http[s]?://www\\.biquge5200\\.com/")) {
            page.setJump(true);
        }
        if (page.getRequest().getUrl().matches("http[s]?://www\\.biquge5200\\.com/\\S+/")) {
            page.setJump(true);
        }
        if (page.getRequest().getUrl().matches("http[s]?//www\\.biquge5200\\.com/\\w+/")) {
            page.setJump(true);
        }
        page.addTargetRequests(page.getHtml().links().regex("http[s]?://www\\.biquge5200\\.com/\\S+/").all());
        page.addTargetRequests(page.getHtml().links().regex("http[s]?://www\\.biquge5200\\.com/\\S+/\\d+.html").all());
        page.addTargetRequests(page.getHtml().links().regex("//www\\.biquge5200\\.com/\\w+/").all());
        page.putField("title", page.getHtml().xpath("//div[@class='bookname']/h1/text()"));
        page.putField("content", page.getHtml().xpath("//div[@id='content']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new BiQuGePageProcessor()).addPipeline(new ConsolePipeline()).addPipeline(new FilePipeline()).setScheduler(new FileCachQueueSchduler()).addUrls("https://www.biquge5200.com/").thread(5).run();
    }
}
