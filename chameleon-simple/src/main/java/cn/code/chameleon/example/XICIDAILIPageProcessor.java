package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.pipeline.ConsolePipeline;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.scheduler.FileCachQueueSchduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-26 下午7:37
 */
public class XICIDAILIPageProcessor implements PageProcessor {

    private Site site = Site.init().setCycleRetryTimes(3).setSleepTime(3000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        if (page.getRequest().getUrl().matches("http://www\\.xicidaili\\.com/articles/")) {
            page.setJump(true);
        }
        page.addTargetRequests(page.getHtml().links().regex("http[s]?://www\\.xicidaili\\.com/[\\w]+/").all());
        page.addTargetRequests(page.getHtml().links().regex("/[\\w]+/").all());
        page.addTargetRequests(page.getHtml().links().regex("/[\\w]+/[\\d]+").all());
        List<String> temps = page.getHtml().xpath("//table[@id='ip_list']/tbody/tr").all();
        List<Map<String, Object>> proxies = new ArrayList<>();
        int i = 1;
        for (String temp : temps) {
            if (i++ == 1) {
                continue;
            }
            Map<String, Object> proxy = new HashMap<>();
            proxy.put("host", page.getHtml().xpath("//*[@id='ip_list']/tbody/tr[" + i + "]/td[2]/text()").get());
            proxy.put("port", page.getHtml().xpath("//*[@id='ip_list']/tbody/tr[" + i + "]/td[3]/text()").get());
            proxy.put("type", page.getHtml().xpath("//*[@id='ip_list']/tbody/tr[" + i + "]/td[6]/text()").get());
            proxy.put("location", page.getHtml().xpath("//*[@id='ip_list']/tbody/tr[" + i + "]/td[4]/a/text()").get());
            proxy.put("speed", page.getHtml().xpath("//*[@id='ip_list']/tbody/tr[" + i + "]/td[7]/div/@title").get());
            proxy.put("connectTime", page.getHtml().xpath("//*[@id='ip_list']/tbody/tr[" + i + "]/td[8]/div/@title").get());
            proxy.put("aliveTime", page.getHtml().xpath("//*[@id='ip_list']/tbody/tr[" + i + "]/td[9]/text()").get());
            proxy.put("validateTime", page.getHtml().xpath("//*[@id='ip_list']/tbody/tr[" + i + "]/td[10]/text()").get());
            proxies.add(proxy);
        }
        page.putField("proxies", proxies);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new XICIDAILIPageProcessor()).addPipeline(new FilePipeline()).addPipeline(new ConsolePipeline()).addUrls("http://www.xicidaili.com/nn/").thread(1).run();
    }
}
