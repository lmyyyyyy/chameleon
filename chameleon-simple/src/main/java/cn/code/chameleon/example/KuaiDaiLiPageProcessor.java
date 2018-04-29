package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.pipeline.ConsolePipeline;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-29 下午12:45
 */
public class KuaiDaiLiPageProcessor implements PageProcessor {

    private Site site = Site.init().setCycleRetryTimes(3).setSleepTime(3000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        List<Map<String, Object>> proxies = new ArrayList<>();
        if (page.getRequest().getUrl().matches("https://www\\.kuaidaili\\.com/free[/\\w+]*[/\\d+]*")) {
            page.addTargetRequests(page.getHtml().links().regex("/free/inha/[\\d+]*").all());
            page.addTargetRequests(page.getHtml().links().regex("/free/intr/[\\d+]*").all());
            List<String> temps = page.getHtml().xpath("//*[@id='list']/table/tbody/tr").all();
            int i = 1;
            for (String temp : temps) {
                Map<String, Object> proxy = new HashMap<>();
                proxy.put("host", page.getHtml().xpath("//*[@id='list']/table/tbody/tr[" + i + "]/td[1]/text()"));
                proxy.put("port", page.getHtml().xpath("//*[@id='list']/table/tbody/tr[" + i + "]/td[2]/text()"));
                proxy.put("type", page.getHtml().xpath("//*[@id='list']/table/tbody/tr[" + i + "]/td[4]/text()"));
                proxy.put("location", page.getHtml().xpath("//*[@id='list']/table/tbody/tr[" + i + "]/td[5]/text()"));
                proxy.put("speed", page.getHtml().xpath("//*[@id='list']/table/tbody/tr[" + i + "]/td[6]/text()"));
                proxy.put("validateTime", page.getHtml().xpath("//*[@id='list']/table/tbody/tr[" + i + "]/td[7]/text()"));
                proxies.add(proxy);
                i++;
            }
        } else if (page.getRequest().getUrl().matches("https://www\\.kuaidaili\\.com/ops/proxylist/[\\d+]")) {
            page.addTargetRequests(page.getHtml().links().regex("/ops/proxylist/\\d+").all());
            List<String> temps = page.getHtml().xpath("//*[@id=\"freelist\"]/table/tbody/tr").all();
            int i = 1;
            for (String temp : temps) {
                Map<String, Object> proxy = new HashMap<>();
                proxy.put("host", page.getHtml().xpath("//*[@id='freelist']/table/tbody/tr[" + i + "]/td[1]/text()"));
                proxy.put("port", page.getHtml().xpath("//*[@id='freelist']/table/tbody/tr[" + i + "]/td[2]/text()"));
                proxy.put("type", page.getHtml().xpath("//*[@id='freelist']/table/tbody/tr[" + i + "]/td[4]/text()"));
                proxy.put("location", page.getHtml().xpath("//*[@id='freelist']/table/tbody/tr[" + i + "]/td[6]/text()"));
                proxy.put("speed", page.getHtml().xpath("//*[@id='freelist']/table/tbody/tr[" + i + "]/td[7]/text()"));
                proxy.put("validateTime", page.getHtml().xpath("//*[@id='freelist']/table/tbody/tr[" + i + "]/td[8]/text()"));
                proxies.add(proxy);
                i++;
            }
        } else {
            page.setJump(true);
        }
        page.putField("proxies", proxies);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new KuaiDaiLiPageProcessor()).addPipeline(new FilePipeline()).addPipeline(new ConsolePipeline()).addUrls("https://www.kuaidaili.com/free/inha/1", "https://www.kuaidaili.com/ops/proxylist/1").thread(1).run();
    }
}
