package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.downloader.PhantomJSDownloader;
import cn.code.chameleon.processor.PageProcessor;

/**
 * @author liumingyu
 * @create 2018-04-21 下午4:05
 */
public class PhantomJSPageProcess implements PageProcessor{

    private Site site = Site.init()
            .setCycleRetryTimes(3)
            .setSleepTime(3000)
            .setTimeOut(10000)
            .setCharset("GBK")
            .setDomain("s.taobao.com")
            .addHeader("Referer", "http://www.taobao.com/");

    @Override
    public void process(Page page) {
        if (page.getRawText() != null) {
            page.putField("title", page.getHtml().xpath("//div[@class='row row-2 title']/a/text()"));
            page.putField("price", page.getHtml().xpath("//div[@class='price g_price g_price-highlight']/strong/text()"));
            page.putField("recivers", page.getHtml().xpath("//div[@class='deal-cnt']/text()"));
            page.putField("shopName", page.getHtml().xpath("//div[@class='shop']/a/span[2]/text()"));
            page.putField("location", page.getHtml().xpath("//div[@class='row row-3 g-clearfix']/div[@class='location']/text()"));

        } else {
            page.setJump(true);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        PhantomJSDownloader phantomDownloader =
                new PhantomJSDownloader("/Users/liumingyu/data/chameleon/phantomjs-2.1.1-macosx/bin/phantomjs", "/Users/liumingyu/data/chameleon/crawl.js").setRetryTimes(3);
        Spider.create(new PhantomJSPageProcess())
                .setDownload(phantomDownloader)
                .addUrls("http://s.taobao.com/search?q=%B6%AC%D7%B0&sort=sale-desc")
                .thread((Runtime.getRuntime().availableProcessors() - 1) << 1)
                .run();
    }
}
