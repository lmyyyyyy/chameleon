package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.downloader.HttpClientDownloader;
import cn.code.chameleon.monitor.SpiderMonitor;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.proxy.Proxy;
import cn.code.chameleon.proxy.SimpleProxyProvider;

/**
 * @author liumingyu
 * @create 2018-04-17 下午7:42
 */
public class ZhihuPageProcess implements PageProcessor {

    private Site site = Site.init().setTimeOut(10000).setRetryTimes(3).setRetryTimes(300).setCycleRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("https://www\\.zhihu\\.com/question/\\d+/answer/\\d+.*").all());
        page.putField("title", page.getHtml().xpath("//h1[@class='QuestionHeader-title']/text()").toString());
        page.putField("question", page.getHtml().xpath("//div[@class='QuestionRichText']/text()").toString());
        page.putField("answer", page.getHtml().xpath("//div[@class='QuestionAnswer-content']/tidyText()").toString());
        if (page.getResults().get("title") == null) {
            page.setJump(true);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.store(new Proxy("1.196.161.172", 9999, "17611030819", "a532033837")));
        Spider.create(new ZhihuPageProcess()).setDownload(httpClientDownloader).thread(10).addUrls("https://www.zhihu.com/search?q=java&type=content").addSpiderListener(new SpiderMonitor()).run();

    }
}
