package cn.code.chameleon.download;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.downloader.PhantomJSDownloader;
import cn.code.chameleon.downloader.SeleniumDownloader;

import java.util.UUID;

/**
 * @author liumingyu
 * @create 2018-04-24 下午3:18
 */
public class PhantomJSDownloaderTest {

    private static Task task = new Task() {
        @Override
        public Site getSite() {
            return Site.init();
        }

        @Override
        public String getUUID() {
            return UUID.randomUUID().toString();
        }
    };

    public static void main(String[] args) {
        PhantomJSDownloader phantomDownloader =
                new PhantomJSDownloader("/Users/liumingyu/data/chameleon/phantomjs-2.1.1-macosx/bin/phantomjs", "/Users/liumingyu/data/chameleon/crawl.js").setRetryTimes(3);
        SeleniumDownloader seleniumDownloader = new SeleniumDownloader("Users/data/chameleon/chromedriver");
        seleniumDownloader.setThread(5);
        seleniumDownloader.setSleepTime(3000);
        Page page = phantomDownloader.download(new Request("https://www.zhihu.com/question/31364849/answer/335672599"), task);
        System.out.println(page.getRawText());
    }
}
