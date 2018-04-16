package cn.code.chameleon.scheduler;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.scheduler.component.BloomFilterDuplicateRemover;
import cn.code.chameleon.scheduler.component.DuplicateRemover;
import org.junit.Test;

import java.util.UUID;

/**
 * @author liumingyu
 * @create 2018-04-16 下午4:36
 */
public class DuplicateRemoverSchedulerTest {

    private DuplicateRemoveScheduler duplicateRemoveScheduler = new DuplicateRemoveScheduler() {
        @Override
        protected void pushWhenNoDuplicate(Request request, Task task) {

        }

        @Override
        public Request poll(Task task) {
            return null;
        }

        @Override
        public Request peek(Task task) {
            return null;
        }
    };

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
    public void test_duplicateRemover_scheduler() throws Exception {
        DuplicateRemover duplicateRemover = duplicateRemoveScheduler.getDuplicateRemover();
        Request request = new Request("https://www.baidu.com/3");
        System.out.println("HashSet is duplicate : " + duplicateRemover.isDuplicate(request, task));

        request = new Request("https://www.baidu.com/3");
        System.out.println("HashSet is duplicate : " + duplicateRemover.isDuplicate(request, task));

        BloomFilterDuplicateRemover bloomFilterDuplicateRemover = new BloomFilterDuplicateRemover(1);
        request = new Request("https://www.baidu.com/3");
        System.out.println("BloomFilter is duplicate : " + bloomFilterDuplicateRemover.isDuplicate(request, task));

        request = new Request("https://www.baidu.com/3");
        System.out.println("BloomFilter is duplicate : " + bloomFilterDuplicateRemover.isDuplicate(request, task));

    }

}
