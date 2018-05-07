package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.downloader.PhantomJSDownloader;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.selector.Html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-28 上午11:15
 */
public class BaoManPageProcessor implements PageProcessor {

    private Site site = Site.init().setTimeOut(10000).setSleepTime(3000).setCycleRetryTimes(3);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("http[s]?://baozoumanhua\\.com/text\\?page=[\\d]+\\&sv=[\\d]+").all());
        List<String> temps = page.getHtml().xpath("//div[@class='article']").all();
        List<Map<String, Object>> jokes = new ArrayList<>();
        for (String temp : temps) {
            Map<String, Object> joke = new HashMap<>();
            Html html = new Html(temp);
            joke.put("userName", html.xpath("//a[@class='article-author-name']/text()"));
            joke.put("date", html.xpath("//span[@class='article-date']/text()"));
            joke.put("commentCount", html.xpath("//a[@class='article-comment-count']/span/text()"));
            joke.put("content", html.xpath("//div[@class='article-content']/h4/a/text()"));
            jokes.add(joke);
        }
        page.putField("jokes", jokes);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new BaoManPageProcessor()).setDownload(new PhantomJSDownloader("/Users/liumingyu/data/chameleon/phantomjs-2.1.1-macosx/bin/phantomjs"
                , "/Users/liumingyu/data/chameleon/crawl.js")).addUrls("http://baozoumanhua.com/text").thread(5).run();
    }
}
