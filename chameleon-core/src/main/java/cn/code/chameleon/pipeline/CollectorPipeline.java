package cn.code.chameleon.pipeline;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-11 下午6:18
 */
public interface CollectorPipeline<T> extends Pipeline {

    List<T> getCollected();
}
