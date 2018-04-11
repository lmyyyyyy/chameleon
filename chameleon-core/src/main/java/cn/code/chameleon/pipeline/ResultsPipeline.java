package cn.code.chameleon.pipeline;

import cn.code.chameleon.carrier.Results;
import cn.code.chameleon.carrier.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-11 下午6:19
 */
public class ResultsPipeline implements CollectorPipeline<Results> {

    private List<Results> collector = new ArrayList<>();

    @Override
    public List<Results> getCollected() {
        return collector;
    }

    @Override
    public synchronized void process(Results results, Task task) {
        collector.add(results);
    }
}
