package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.downloader.PhantomJSDownloader;
import cn.code.chameleon.pipeline.ConsolePipeline;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.scheduler.FileCachQueueSchduler;

/**
 * @author liumingyu
 * @create 2018-04-24 下午2:42
 */
public class JueJinPageProcessor implements PageProcessor {

    private Site site = Site.init().setTimeOut(10000).setSleepTime(3000).setCycleRetryTimes(3);

    @Override
    public void process(Page page) {
        if (page.getRequest().getUrl().matches("https://juejin\\.im/")) {
            page.setJump(true);
        }
        page.addTargetRequests(page.getHtml().links().regex("/post/[\\w]+").all());
        page.putField("title", page.getHtml().xpath("//h1[@class='article-title']/text()"));
        page.putField("author", page.getHtml().xpath("//div[@class='username']/text()"));
        page.putField("date", page.getHtml().xpath("//div[@class='meta-box']/time/text()"));
        page.putField("loveCount", page.getHtml().$("div.like-btn", "badge"));
        page.putField("commentCount", page.getHtml().$("div.comment-btn", "badge"));
        page.putField("content", page.getHtml().xpath("//div[@class='article-content']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        PhantomJSDownloader phantomDownloader =
                new PhantomJSDownloader("/Users/liumingyu/data/chameleon/phantomjs-2.1.1-macosx/bin/phantomjs", "/Users/liumingyu/data/chameleon/crawl.js").setRetryTimes(3);
        Spider.create(new JueJinPageProcessor()).addPipeline(new ConsolePipeline()).addPipeline(new FilePipeline()).setScheduler(new FileCachQueueSchduler()).setDownload(phantomDownloader).addUrls("https://juejin.im/").thread(5).run();
    }
}
