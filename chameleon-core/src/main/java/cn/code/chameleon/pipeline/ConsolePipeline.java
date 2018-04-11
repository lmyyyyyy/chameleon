package cn.code.chameleon.pipeline;

import cn.code.chameleon.carrier.Results;
import cn.code.chameleon.carrier.Task;

import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-11 下午6:17
 */
public class ConsolePipeline implements Pipeline {

    @Override
    public void process(Results results, Task task) {
        System.out.println("get results from this url: " + results.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : results.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
    }
}
