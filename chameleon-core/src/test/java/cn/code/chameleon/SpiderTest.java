package cn.code.chameleon;

import cn.code.chameleon.carrier.Results;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.processor.SimplePageProcessor;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author liumingyu
 * @create 2018-04-17 下午8:34
 */
public class SpiderTest {

    @Ignore("long time")
    @Test
    public void testStartAndStop() throws InterruptedException {
        Spider spider = Spider.create(new SimplePageProcessor("http://www.oschina.net/*")).addPipeline((Results results, Task task) -> {
                    System.out.println(1);
                }
        ).thread(1).addUrls("http://www.oschina.net/");
        spider.start();
        System.out.println("开始");
        Thread.sleep(10000);
        spider.stop();
        System.out.println("暂停");
        Thread.sleep(10000);
        spider.start();
        System.out.println("再开始");
        Thread.sleep(10000);
    }
}
