package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.scheduler.FileCachQueueSchduler;

/**
 * @author liumingyu
 * @create 2018-04-24 上午10:59
 */
public class CSDNBlogPageProcessor implements PageProcessor {

    private Site site = Site.init()
            .setTimeOut(10000)
            .setCycleRetryTimes(3)
            .setSleepTime(3000)
            .setCharset("utf-8");

    @Override
    public void process(Page page) {
        if (page.getRequest().getUrl().matches("http[s]?://blog\\.csdn\\.net/")) {
            page.setJump(true);
        }
        page.addTargetRequests(page.getHtml().links().regex("(http[s]?\\://blog\\.csdn\\.net/[\\w]+/[\\w]+/[\\w]+/[\\w]+)").all());
        String title = page.getHtml().$("h1.csdn_top", "text").get();
        if (title == null || "".equals(title.trim())) {
            title = page.getHtml().$("span.link_title a", "text").get();
            if (title == null || "".equals(title.trim())) {
                page.setJump(true);
            }
        }
        page.putField("title", title);

        page.putField("author", page.getHtml().$("#uid", "text"));
        String date = page.getHtml().xpath("//div[@class='artical_tag']/span[@class='time']/text()").get();
        if (date == null || "".equals(date.trim())) {
            date = page.getHtml().xpath("//div[@class='article_r']/span[@class='link_postdate']/text()").get();
        }
        page.putField("date", date);
        String content = page.getHtml().xpath("//div[@class='markdown_views']/tidyText()").get();
        if (content == null || "".equals(content.trim())) {
            content = page.getHtml().xpath("//div[@class='htmledit_views']/tidyText()").get();
            if (content == null || "".equals(content.trim())) {
                page.setJump(true);
            }
        }
        page.putField("content", content);

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new CSDNBlogPageProcessor()).addPipeline(new FilePipeline()).setScheduler(new FileCachQueueSchduler()).addUrls("https://blog.csdn.net/").thread(5).run();
    }
}
