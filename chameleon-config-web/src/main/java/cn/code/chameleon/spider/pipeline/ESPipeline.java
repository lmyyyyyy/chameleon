package cn.code.chameleon.spider.pipeline;


import cn.code.chameleon.carrier.Results;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.model.ESClient;
import cn.code.chameleon.pipeline.Pipeline;

/**
 * @author liumingyu
 * @create 2018-05-15 上午10:27
 */
public class ESPipeline implements Pipeline {

    private ESClient esClient;

    public ESPipeline(ESClient esClient) {
        this.esClient = esClient;
    }

    @Override
    public void process(Results results, Task task) {
        if (results.isJump()) {
            return;
        }
        if (esClient == null) {
            esClient = new ESClient();
        }
        esClient.save(task.getSite().getDomain(),
                task.getUUID(),
                results.getRequest().getUrl(),
                results.getAll());
    }
}
