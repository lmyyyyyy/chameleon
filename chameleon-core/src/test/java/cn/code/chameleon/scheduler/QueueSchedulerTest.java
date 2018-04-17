package cn.code.chameleon.scheduler;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.scheduler.component.BloomFilterDuplicateRemover;
import org.junit.Test;

import java.util.UUID;

/**
 * @author liumingyu
 * @create 2018-04-16 下午3:36
 */
public class QueueSchedulerTest {

    private QueueScheduler queueScheduler = new QueueScheduler();

    private Task task = new Task() {
        @Override
        public Site getSite() {
            return null;
        }

        @Override
        public String getUUID() {
            return UUID.randomUUID().toString();
        }
    };

    @Test
    public void test_queue_scheduler() throws Exception {

        //queueScheduler.setDuplicateRemover(new BloomFilterDuplicateRemover(10));

        Request request = new Request("https://www.baidu.com/1");
        queueScheduler.push(request, task);

        request = new Request("https://www.baidu.com/2");
        queueScheduler.push(request, task);

        request = new Request("https://www.baidu.com/3");
        queueScheduler.push(request, task);

        request = new Request("https://www.baidu.com/4");
        queueScheduler.push(request, task);

        request = new Request("https://www.baidu.com/2");
        queueScheduler.push(request, task);

        int totalCount = queueScheduler.getTotalRequestsCount(task);

        int leftCount = queueScheduler.getLeftRequestsCount(task);

        while (leftCount > 0) {
            System.out.println("left count : " + leftCount + "; total count : " + totalCount);
            Request poll = queueScheduler.poll(task);
            System.out.println(poll);
            leftCount = queueScheduler.getLeftRequestsCount(task);
            totalCount = queueScheduler.getTotalRequestsCount(task);
        }
    }
}
