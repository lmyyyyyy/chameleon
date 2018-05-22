package cn.code.chameleon.spider;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.model.SpiderTemplate;
import cn.code.chameleon.model.TaskManager;
import cn.code.chameleon.model.TaskStatisticsManager;
import cn.code.chameleon.pojo.ChameleonStatistics;
import cn.code.chameleon.pojo.ChameleonTask;
import cn.code.chameleon.processor.PageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liumingyu
 * @create 2018-05-12 下午8:48
 */
public class CommonSpider extends Spider {

    private final Logger LOGGER = LoggerFactory.getLogger(CommonSpider.class);

    private SpiderTemplate spiderTemplate;

    private TaskManager taskManager;

    private TaskStatisticsManager taskStatisticsManager;

    public CommonSpider(PageProcessor pageProcessor, SpiderTemplate spiderTemplate) {
        super(pageProcessor);
        this.spiderTemplate = spiderTemplate;
    }

    @Autowired
    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Autowired
    public void setTaskStatisticsManager(TaskStatisticsManager taskStatisticsManager) {
        this.taskStatisticsManager = taskStatisticsManager;
    }

    @Override
    protected void onSuccess(Request request) {
        super.onSuccess(request);
        ChameleonTask task = taskManager.getTaskById(this.getUUID());
        if (task == null) {
            this.stop();
            taskStatisticsManager.incrStop(task.getId());
        }
        ChameleonStatistics statistics = taskStatisticsManager.getTaskStatisticsById(task.getId());
        if (statistics == null) {
            this.stop();
            taskStatisticsManager.incrStop(task.getId());
        }
        if (this.getPageCount() >= spiderTemplate.getMaxCount() && this.getStatus().equals(Status.Running)) {
            this.stop();
            taskStatisticsManager.incrStop(task.getId());
        }
        taskStatisticsManager.setCrawl(task.getId(), this.getPageCount());
    }

    @Override
    protected void onError(Request request) {
        super.onError(request);
        ChameleonTask task = taskManager.getTaskById(this.getUUID());
        if (task == null) {
            this.stop();
            taskStatisticsManager.incrStop(task.getId());
        }
        ChameleonStatistics statistics = taskStatisticsManager.getTaskStatisticsById(task.getId());
        if (statistics == null) {
            this.stop();
            taskStatisticsManager.incrStop(task.getId());
        }
        taskStatisticsManager.incrError(task.getId());
    }

    @Override
    public void close() {
        super.close();
        ChameleonTask task = taskManager.getTaskById(this.getUUID());
        if (task != null) {

        }
        ChameleonStatistics statistics = taskStatisticsManager.getTaskStatisticsById(task.getId());
        if (statistics == null) {
            this.stop();
            taskStatisticsManager.incrStop(task.getId());
        }
    }
}
