package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.processor.PageProcessor;

/**
 * @author liumingyu
 * @create 2018-04-24 下午8:21
 */
public class RunoobPageProcessor implements PageProcessor {

    private Site site = Site.init().setTimeOut(10000).setSleepTime(3000).setCycleRetryTimes(3).setDomain("www.runoob.com");

    @Override
    public void process(Page page) {
        if (page.getRequest().getUrl().matches("http[s]?://www\\.runoob\\.com")) {
            page.setJump(true);
        }
        if (page.getRequest().getUrl().matches("http[s]?://www\\.runoob\\.com/[\\w]+/[\\w]+/")) {
            page.setJump(true);
        }
        if (page.getRequest().getUrl().matches("http[s]?://www]\\.runoob\\.com/quiz/[\\w]*.*")) {
            page.setJump(true);
        }
        page.addTargetRequests(page.getHtml().links().regex("/[\\w]+/[\\w]+-[\\w]+.+").all());
        page.addTargetRequests(page.getHtml().links().regex("[/]*/www\\.runoob\\.com//[\\w]+/[\\w]+-[\\w]+.+").all());
        String title = page.getHtml().xpath("//div[@class='article-intro']/h1/tidyText()").get();
        if (title == null || "".equals(title)) {
            page.setJump(true);
        }
        page.putField("title", title);
        page.putField("content", page.getHtml().xpath("//div[@class='article-intro']/tidyText()"));
        String preTitle = page.getHtml().xpath("//div[@class='previous-design-link']/a/text()").get();
        if (preTitle != null && !"".equals(preTitle.trim())) {
            page.putField("preTitle", preTitle);
            page.putField("preUrl", page.getHtml().$("div.previous-design-link a", "href"));
        }
        String nextTitle = page.getHtml().xpath("//div[@class='next-design-link']/a/text()").get();
        if (nextTitle != null && !"".equals(nextTitle.trim())) {
            page.putField("nextTitle", nextTitle);
            page.putField("nextUrl", page.getHtml().$("div.next-design-link a", "href"));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new RunoobPageProcessor()).addPipeline(new FilePipeline()).addUrls("http://www.runoob.com/").thread(5).run();
    }
}
