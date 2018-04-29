package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-29 上午11:56
 */
public class IP66PageProcessor implements PageProcessor {

    private Site site = Site.init().setTimeOut(10000).setSleepTime(3000).setCycleRetryTimes(3)
            .addHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)");

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("/\\d+.*").all());
        List<String> temps = page.getHtml().xpath("//*[@id='main']/div/div[1]/table/tbody/tr").all();
        List<Map<String, Object>> proxies = new ArrayList<>();
        int i = 1;
        for (String temp : temps) {
            if (i++ == 1) {
                continue;
            }
            Map<String, Object> proxy = new HashMap<>();
            proxy.put("host", page.getHtml().xpath("//*[@id='main']/div/div[1]/table/tbody/tr["+i+"]/td[1]/text()"));
            proxy.put("port", page.getHtml().xpath("//*[@id='main']/div/div[1]/table/tbody/tr["+i+"]/td[2]/text()"));
            proxy.put("location", page.getHtml().xpath("//*[@id='main']/div/div[1]/table/tbody/tr["+i+"]/td[3]/text()"));
            proxy.put("type", page.getHtml().xpath("//*[@id='main']/div/div[1]/table/tbody/tr["+i+"]/td[4]/text()"));
            proxy.put("validateTime", page.getHtml().xpath("//*[@id='main']/div/div[1]/table/tbody/tr["+i+"]/td[5]/text()"));
            proxies.add(proxy);
        }
        page.putField("proxies", proxies);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new IP66PageProcessor()).addUrls("http://www.66ip.cn/index.html").thread(1).run();
    }
}
