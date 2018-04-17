package cn.code.chameleon.processor;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.downloader.HttpClientDownloader;
import org.junit.Test;

import java.util.UUID;

/**
 * @author liumingyu
 * @create 2018-04-17 下午5:44
 */
public class SimplePageProcessTest {

    private Task task = new Task() {
        @Override
        public Site getSite() {
            return Site.init();
        }

        @Override
        public String getUUID() {
            return UUID.randomUUID().toString();
        }
    };

    @Test
    public void test_process() throws Exception {
        SimplePageProcessor simplePageProcess = new SimplePageProcessor("http://www.lblogg.cn/*");
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        Request request = new Request("http://www.lblogg.cn/single/138");
        Page page = httpClientDownloader.download(request, task);
        System.out.println(page.getRawText());
        simplePageProcess.process(page);
        System.out.println("===========================");
        System.out.println(page.getResults().getAll().get("title"));
        System.out.println(page.getResults().getAll().get("content"));
    }
}
