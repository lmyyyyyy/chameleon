package cn.code.chameleon.downloader;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.carrier.Task;
import org.junit.Test;

import java.util.UUID;

/**
 * @author liumingyu
 * @create 2018-04-23 下午8:11
 */
public class SeleniumDownloadTest {

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
    public void test_download() throws Exception {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        Request request = new Request("https://play.google.com/store/apps/details?id=com.tencent.mm&showAllReviews=true");
        SeleniumDownloader seleniumDownloader = new SeleniumDownloader("Users/data/chameleon/chromedriver");
        Page page = seleniumDownloader.download(request, task);
        System.out.println(page.getRawText());
    }
}
