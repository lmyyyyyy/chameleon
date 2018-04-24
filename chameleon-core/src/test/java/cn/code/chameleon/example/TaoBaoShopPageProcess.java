package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.downloader.SeleniumDownloader;
import cn.code.chameleon.processor.PageProcessor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liumingyu
 * @create 2018-04-23 下午4:55
 */
public class TaoBaoShopPageProcess implements PageProcessor {

    private Site site = Site.init()
            .setCharset("utf-8")
            .setCycleRetryTimes(3)
            .setSleepTime(3000)
            .addHeader("Connection", "keep-alive")
            .addHeader("Cache-Control", "max-age=0")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");

    @Override
    public void process(Page page) {
        if (isListPage(page)) {
            page.addTargetRequests(page.getHtml().$(".J_ClickStat", "href").all());
        } else {
            page.addTargetRequests(page.getHtml().links().regex("(//item\\.taobao\\.com/item\\.htm\\?spm=.+)").all());
        }
        //page.addTargetRequests(page.getHtml().links().regex("(//detail\\.tmall\\.com/item\\.htm\\?spm=.+)").all());

        String curUrl = page.getUrl().get();
        boolean isTaoBao = curUrl.startsWith("https://item.taobao.com");
        boolean isTmall = curUrl.startsWith("https://detail.tmall.com");
        String tempSpm = curUrl.split("\\?")[1].split("&")[0];

        String spm = tempSpm.split("=")[1];

        String shopUrl = "";

        String name = "";

        double price = 0;

        int sellCount = 0;

        double allPrice = 0;

        String temp;
        if (isTaoBao) {

            shopUrl = page.getHtml().xpath("//div[@class='tb-shop-name']/dl/dd/strong/a/@href").get();
            if (shopUrl == null || "".equals(shopUrl)) {
                shopUrl = page.getHtml().$("a.shop-name-link", "href").get();
            }
            name = page.getHtml().xpath("//*[@id='J_Title']/h3/text()").get();

            try {
                temp = page.getHtml().$("#J_PromoPriceNum", "text").get().trim();
                if (temp.contains("-")) {
                    price = Double.valueOf(temp.split("-")[0]);
                } else {
                    price = Double.valueOf(temp);
                }
            } catch (Exception e) {
                page.setJump(true);
            }
            temp = page.getHtml().$("#J_SellCounter", "text").get();
            if (temp.contains("-")) {
                page.addTargetRequest(page.getRequest().getUrl());
                page.setJump(true);
                return ;
            } else {
                sellCount = Integer.valueOf(temp);
            }
            allPrice = Double.valueOf(price) * Double.valueOf(sellCount);
        } else if (isTmall) {
            shopUrl = page.getHtml().xpath("//div[@class='slogo']/a/@href").get();

            name = page.getHtml().$(".tb-detail-hd h1", "text").get().trim();
            temp = page.getHtml().$(".tm-price", "text").get().trim();
            if (temp.contains("-")) {
                price = Double.valueOf(temp.split("-")[0]);
            } else {
                price = Double.valueOf(temp);
            }
            temp = page.getHtml().$(".tm-count", "text").get().trim();
            if (temp.contains("-")) {
                page.addTargetRequest(page.getRequest().getUrl());
                page.setJump(true);
                return ;
            } else {
                sellCount = Integer.valueOf(temp);
            }
            allPrice = Double.valueOf(price) * Double.valueOf(sellCount);
        } else {
            page.setJump(true);
        }

        page.putField("shopUrl", shopUrl);
        page.putField("spm", spm);
        page.putField("name", name);
        page.putField("price", price);
        page.putField("sellCount", sellCount);
        page.putField("allPrice", allPrice);
    }

    private boolean isListPage(Page page) {
        String tmp = page.getHtml().$("#J_PromoPrice").get();
        if (StringUtils.isBlank(tmp)) {
            return true;
        }
        return false;
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        SeleniumDownloader seleniumDownloader = new SeleniumDownloader("Users/data/chameleon/chromedriver");
        seleniumDownloader.setThread(5);
        seleniumDownloader.setSleepTime(3000);
        Spider.create(new TaoBaoShopPageProcess())
                .setDownload(seleniumDownloader)
                .addUrls("http://s.taobao.com/search?q=%B6%AC%D7%B0&sort=sale-desc")
                .thread(5)
                .run();
    }
}
