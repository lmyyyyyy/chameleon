package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.processor.PageProcessor;

/**
 * @author liumingyu
 * @create 2018-04-25 下午8:19
 */
public class NiukePageProcessor implements PageProcessor {

    private Site site = Site.init().setTimeOut(10000).setSleepTime(3000).setCycleRetryTimes(3);

    @Override
    public void process(Page page) {
        if (page.getRequest().getUrl().matches("http[s]?://www\\.nowcoder\\.com/discuss*")) {
            page.setJump(true);
        }
        if (page.getRequest().getUrl().matches("/discurss")) {
            page.setJump(true);
        }
        if (page.getRequest().getUrl().matches("/discuss\\?type=[\\d]+&order=[\\d]+&page=[\\d]+")) {
            page.setJump(true);
        }
        if (page.getRequest().getUrl().matches("http[s]?://www\\.nowcoder\\.com/discuss\\?type=[\\d]+&order=[\\d]+&page=[\\d]+")) {
            page.setJump(true);
        }
        page.addTargetRequests(page.getHtml().links().regex("/profile/[\\d]+").all());
        page.addTargetRequests(page.getHtml().links().regex("http[s]?://www\\.nowcoder\\.com/profile/[\\d]+").all());
        page.addTargetRequests(page.getHtml().links().regex("/discuss\\?type=[\\d]+&order=[\\d]+&page=[\\d]+").all());
        page.putField("nikeName", page.getHtml().xpath("//a[@class='profile-user-name']/text()"));
        String sex = page.getHtml().$("span.sex-ico", "title").get();
        if (sex != null && !"".equals(sex)) {
            page.putField("sex", page.getHtml().$("span.sex-ico", "title"));
        }
        page.putField("city", page.getHtml().xpath("//li[@class='profile-city']/text()"));
        page.putField("school", page.getHtml().xpath("//li[@class='profile-edu']/text()"));
        page.putField("achievement", page.getHtml().xpath("//div[@class='module-body']/div[1]/a/div/span/text()"));
        page.putField("badge", page.getHtml().xpath("//div[@class='module-body']/div[2]/a/div/span/text()"));
        String about = page.getHtml().xpath("//div[@class='module-box']/div[@class='module-body']/div[4]/div[2]/span[1]/a/text()").get();
        if (about == null || "".equals(about.trim())) {
            about = page.getHtml().xpath("//div[@class='module-box']/div[@class='module-body']/div[4]/div[2]/span[1]/span[2]/text()").get();
        }
        page.putField("about", about);
        String abouted = page.getHtml().xpath("//div[@class='module-box']/div[@class='module-body']/div[4]/div[2]/span[2]/a/text()").get();
        if (abouted == null || "".equals(abouted.trim())) {
            abouted = page.getHtml().xpath("//div[@class='module-box']/div[@class='module-body']/div[4]/div[2]/span[2]/span[2]/text()").get();
        }
        page.putField("abouted", abouted);
        page.putField("loginCounts", page.getHtml().xpath("//p[@class='sign-num']/text()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new NiukePageProcessor()).addUrls("https://www.nowcoder.com/discuss").thread(5).run();
    }
}
