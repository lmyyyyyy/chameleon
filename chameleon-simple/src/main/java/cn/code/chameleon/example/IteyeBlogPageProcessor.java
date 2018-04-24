package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.processor.PageProcessor;

/**
 * @author liumingyu
 * @create 2018-04-24 下午1:48
 */
public class IteyeBlogPageProcessor implements PageProcessor {

    private Site site = Site.init()
            .setSleepTime(3000)
            .setCycleRetryTimes(3)
            .setTimeOut(10000);

    @Override
    public void process(Page page) {
        if (page.getRequest().getUrl().matches("http[s]?://[\\w]+\\.iteye\\.com") || page.getRequest().getUrl().matches("http[s]?://www\\.iteye\\.com/blogs")) {
            page.setJump(true);
        }
        page.addTargetRequests(page.getHtml().links().regex("(http[s]?://[\\w]+\\.iteye\\.com/blog/[\\d]+)").all());
        page.putField("title", page.getHtml().$("div.blog_title h3 a", "text"));
        page.putField("author", page.getHtml().xpath("//div[@id='blog_owner_name']/text()"));
        page.putField("date", page.getHtml().xpath("//div[@class='blog_bottom']/ul/li[1]/text()"));
        page.putField("views", page.getHtml().xpath("//div[@class='blog_bottom']/ul/li[2]/text()"));
        page.putField("content", page.getHtml().xpath("//div[@id='blog_content']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new IteyeBlogPageProcessor()).addUrls("http://www.iteye.com/blogs").thread(5).run();
    }
}
