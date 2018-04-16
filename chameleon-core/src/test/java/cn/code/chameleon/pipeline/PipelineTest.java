package cn.code.chameleon.pipeline;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Results;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.carrier.Task;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author liumingyu
 * @create 2018-04-16 下午5:05
 */
public class PipelineTest {

    private static Results results;

    private static Task task = new Task() {
        @Override
        public Site getSite() {
            return null;
        }

        @Override
        public String getUUID() {
            return UUID.randomUUID().toString();
        }
    };

    @BeforeClass
    public static void init() throws Exception {
        results = new Results();
        results.put("title", "一种可定制化爬虫");
        results.put("content", "内容不告诉你～");
        Request request = new Request("http://www.baidu.com");
        results.setRequest(request);
    }

    @Test
    public void process_console_pipeline() throws Exception {
        ConsolePipeline consolePipeline = new ConsolePipeline();
        consolePipeline.process(results, task);
    }

    @Test
    public void process_file_pipeline() throws Exception {
        FilePipeline filePipeline = new FilePipeline();
        filePipeline.process(results, task);
    }

    @Test
    public void process_results_pipeline() throws Exception {
        ResultsPipeline resultsPipeline = new ResultsPipeline();
        resultsPipeline.process(results, task);
        List<Results> results = resultsPipeline.getCollected();
        for (Results result : results) {
            System.out.println("get results from this url: " + result.getRequest().getUrl());
            for (Map.Entry<String, Object> entry : result.getAll().entrySet()) {
                System.out.println(entry.getKey() + ":\t" + entry.getValue());
            }
        }
    }
}
