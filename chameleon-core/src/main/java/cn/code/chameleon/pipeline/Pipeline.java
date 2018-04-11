package cn.code.chameleon.pipeline;

import cn.code.chameleon.carrier.Results;
import cn.code.chameleon.carrier.Task;

/**
 * @author liumingyu
 * @create 2018-04-09 下午2:49
 */
public interface Pipeline {

    void process(Results results, Task task);
}
