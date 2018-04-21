package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.downloader.PhantomJSDownloader;
import cn.code.chameleon.processor.PageProcessor;

/**
 * @author liumingyu
 * @create 2018-04-21 下午10:00
 */
public class TaoBaoPageProcess implements PageProcessor {

    private Site site = Site.init()
            .setCycleRetryTimes(3)
            .setSleepTime(3000)
            .setTimeOut(10000)
            .setCharset("GBK")
            //.setDomain("s.taobao.com")
            .addHeader("Referer", "http://www.taobao.com/");

    @Override
    public void process(Page page) {
        if (page.getRawText() != null) {
            page.addTargetRequests(page.getHtml().links().regex("(//item\\.taobao\\.com/item\\.htm\\?spm=.+)").all());
            page.putField("title", page.getHtml().xpath("//div[@class='tb-title']/h3/text()"));
            page.putField("price", page.getHtml().xpath("//div[@class='tb-promo-item-bd']/strong[@class='tb-promo-price']/em[@class='tb-rmb-num']/text()"));
            page.putField("comment", page.getHtml().xpath("//div[@class='tb-rate-counter']/a/strong/text()"));
            page.putField("dealSuccess", page.getHtml().xpath("//div[@class='tb-sell-counter']/a/strong/text()"));
            page.putField("shopName", page.getHtml().xpath("//div[@class='shop-name']/div/a/text()"));
            page.putField("location", page.getHtml().xpath("//div[@class='wl-areainfo clearfix']/span[@id='J_WlAreaInfo']/span[@id='J-From']/text()"));

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
        Spider.create(new TaoBaoPageProcess())
                .setDownload(phantomDownloader)
                //.addUrls("http://s.taobao.com/search?q=%B6%AC%D7%B0&sort=sale-desc")
                .addUrls("https://item.taobao.com/item.htm?spm=a230r.1.14.9.3488289bKM7PUD&id=563197332711&ns=1&abbucket=3#detail")
                .thread(5)
                .run();
    }
}
