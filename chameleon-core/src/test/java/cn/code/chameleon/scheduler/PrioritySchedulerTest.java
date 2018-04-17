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
public class PrioritySchedulerTest {

    private PriorityScheduler priorityScheduler = new PriorityScheduler();

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
    public void test_priority_scheduler() throws Exception {

        //priorityScheduler.setDuplicateRemover(new BloomFilterDuplicateRemover(10));
        Request request = new Request("https://www.baidu.com/3");
        request.setPriority(3);
        priorityScheduler.push(request, task);

        request = new Request("https://www.baidu.com/4");
        priorityScheduler.push(request, task);

        request = new Request("https://www.baidu.com/1");
        request.setPriority(1);
        priorityScheduler.push(request, task);

        request = new Request("https://www.baidu.com/2");
        request.setPriority(2);
        priorityScheduler.push(request, task);

        request = new Request("https://www.baidu.com/-1");
        request.setPriority(-1);
        priorityScheduler.push(request, task);

        request = new Request("https://www.baidu.com/3");
        request.setPriority(1);
        priorityScheduler.push(request, task);

        int leftCount = priorityScheduler.getLeftRequestsCount(task);

        int totalCount = priorityScheduler.getTotalRequestsCount(task);

        while (priorityScheduler.peek(task) != null) {
            System.out.println("left count : " + leftCount + "; total count : " + totalCount);
            Request poll = priorityScheduler.poll(task);
            System.out.println("request : " + poll);
            leftCount = priorityScheduler.getLeftRequestsCount(task);
            totalCount = priorityScheduler.getTotalRequestsCount(task);
        }
    }
}
