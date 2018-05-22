package cn.code.chameleon.service.impl;

import cn.code.chameleon.Spider;
import cn.code.chameleon.downloader.HttpClientDownloader;
import cn.code.chameleon.downloader.PhantomJSDownloader;
import cn.code.chameleon.downloader.SeleniumDownloader;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.model.ESClient;
import cn.code.chameleon.model.SpiderManager;
import cn.code.chameleon.model.SpiderTemplate;
import cn.code.chameleon.model.TaskManager;
import cn.code.chameleon.model.TaskStatisticsManager;
import cn.code.chameleon.pipeline.ConsolePipeline;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.pojo.ChameleonTask;
import cn.code.chameleon.pojo.ChameleonTemplate;
import cn.code.chameleon.quartz.QuartzManager;
import cn.code.chameleon.quartz.SpiderJob;
import cn.code.chameleon.scheduler.FileCachQueueSchduler;
import cn.code.chameleon.scheduler.PriorityScheduler;
import cn.code.chameleon.scheduler.QueueScheduler;
import cn.code.chameleon.scheduler.component.DuplicateRemover;
import cn.code.chameleon.service.ChameleonStatisticsService;
import cn.code.chameleon.service.ChameleonTaskService;
import cn.code.chameleon.service.ChameleonTemplateService;
import cn.code.chameleon.service.GroupService;
import cn.code.chameleon.service.SpiderService;
import cn.code.chameleon.spider.CommonSpider;
import cn.code.chameleon.spider.enums.DownloaderEnum;
import cn.code.chameleon.spider.enums.DuplicateRemoverEnum;
import cn.code.chameleon.spider.enums.PipelineEnum;
import cn.code.chameleon.spider.enums.SchedulerEnum;
import cn.code.chameleon.spider.pipeline.ESPipeline;
import cn.code.chameleon.spider.pipeline.RedisPipeline;
import cn.code.chameleon.spider.processor.CommonPageProcessor;
import cn.code.chameleon.spider.scheduler.RedisPriorityScheduler;
import cn.code.chameleon.spider.scheduler.RedisScheduler;
import cn.code.chameleon.utils.JsonUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-05-12 下午1:57
 */
@Service
public class SpiderServiceImpl implements SpiderService {

    private final String QUARTZ_TRIGGER_GROUP_NAME = "chameleon-spider-trigger";
    private final String QUARTZ_TRIGGER_NAME_SUFFIX = "-hours";

    @Autowired
    private SpiderManager spiderManager;

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private QuartzManager quartzManager;

    @Autowired
    private TaskStatisticsManager taskStatisticsManager;

    @Autowired
    private ChameleonTaskService chameleonTaskService;

    @Autowired
    private ChameleonStatisticsService chameleonStatisticsService;

    @Autowired
    private ChameleonTemplateService chameleonTemplateService;

    @Autowired
    private GroupService groupService;

    /**
     * 爬虫开始
     *
     * @param taskId
     * @param operatorId
     * @return
     * @throws ChameleonException
     */
    @Override
    public String startSpider(Long taskId, Long operatorId) throws ChameleonException {
        ChameleonTask task = chameleonTaskService.queryTaskById(taskId);
        if (task.getExpression() != null && !"".equals(task.getExpression())) {
            this.createQuartzJob(taskId, operatorId);
        } else {
            this.start(taskId, operatorId);
        }
        return task.getJobGroup() + "-" + taskId;
    }

    @Override
    public String start(Long taskId, Long operatorId) throws ChameleonException {
        ChameleonTask task = chameleonTaskService.queryTaskById(taskId);
        return this.start(task, operatorId);
    }

    @Override
    public String start(ChameleonTask task, Long operatorId) throws ChameleonException {
        Long templateId = task.getTemplateId();
        ChameleonTemplate template = chameleonTemplateService.queryTemplateById(templateId);
        SpiderTemplate spiderTemplate = JsonUtils.jsonToPojo(template.getTemplateConfig(), SpiderTemplate.class);
        CommonSpider spider = makeSpider(task, spiderTemplate);

        spiderManager.put(spider.getUUID(), spider);

        if (task.getNeedSync()) {
            spider.runAsync();
        } else {
            spider.start();
        }

        ChameleonTask task1 = taskManager.getTaskById(spider.getUUID());
        if (task1 == null) {
            task.setStatus(Spider.Status.Running.getValue());
            taskManager.put(spider.getUUID(), task);
        } else {
            task1.setStatus(Spider.Status.Running.getValue());
            taskManager.put(spider.getUUID(), task1);
        }
        chameleonTaskService.updateTaskStatus(task.getId(), Spider.Status.Running.getValue(), operatorId);
        return spider.getUUID();
    }

    @Override
    public void start(List<Long> taskIds, Long operatorId) throws ChameleonException {
        for (Long taskId : taskIds) {
            this.start(taskId, operatorId);
        }
    }

    @Override
    public void startByGroupId(Long groupId, Long operatorId) throws ChameleonException {
        List<Long> taskIds = chameleonTaskService.queryTaskIdsByGroupId(groupId);
        this.start(taskIds, operatorId);
    }

    @Override
    public void stop(Long taskId, Long operatorId) throws ChameleonException {
        ChameleonTask task = chameleonTaskService.queryTaskById(taskId);
        if (task.getExpression() != null && !"".equals(task.getExpression())) {
            this.pauseQuartzJob(taskId, operatorId);
            taskManager.pause(task.getJobGroup() + "-" + task.getId());
            chameleonTaskService.updateTaskStatus(taskId, Spider.Status.Pausing.getValue(), operatorId);
        } else {
            CommonSpider commonSpider = spiderManager.getSpiderById(task.getJobGroup() + ":" + task.getId());
            commonSpider.close();
            commonSpider.stop();
            taskManager.stop(task.getJobGroup() + "-" + task.getId());
            chameleonTaskService.updateTaskStatus(taskId, Spider.Status.Stopped.getValue(), operatorId);
        }
    }

    @Override
    public void stopAll(Long operatorId) throws ChameleonException {
        List<CommonSpider> commonSpiders = spiderManager.getRunningSpider();
        for (CommonSpider commonSpider : commonSpiders) {
            ChameleonTask task = taskManager.getTaskById(commonSpider.getUUID());
            if (task.getExpression() != null && !"".equals(task.getExpression())) {
                this.pauseQuartzJob(task.getId(), operatorId);
                taskManager.pause(commonSpider.getUUID());
                chameleonTaskService.updateTaskStatus(task.getId(), Spider.Status.Pausing.getValue(), operatorId);
            } else {
                commonSpider.close();
                commonSpider.stop();
                taskManager.stop(commonSpider.getUUID());
                chameleonTaskService.updateTaskStatus(task.getId(), Spider.Status.Stopped.getValue(), operatorId);
            }
        }
    }

    @Override
    public void delete(Long taskId, Long operatorId) throws ChameleonException {
        ChameleonTask task = chameleonTaskService.queryTaskById(taskId);
        CommonSpider commonSpider = spiderManager.getSpiderById(task.getJobGroup() + ":" + task.getId());
        if (commonSpider.getStatus().equals(Spider.Status.Running)) {
            throw new ChameleonException(ResultCodeEnum.TASK_IS_RUNNING);
        }

        if (task.getExpression() != null && !"".equals(task.getExpression())) {
            this.removeQuartzJob(taskId, operatorId);
        }
        spiderManager.remove(commonSpider.getUUID());
        taskManager.remove(commonSpider.getUUID());
        taskStatisticsManager.remove(taskId);

        chameleonStatisticsService.deleteStatisticByTaskId(taskId, operatorId);
        chameleonTaskService.deleteTaskById(taskId, operatorId);
    }

    @Override
    public void deleteAll(Long operatorId) throws ChameleonException {
        List<ChameleonTask> tasks = taskManager.all();
        for (ChameleonTask task : tasks) {
            if (task == null) {
                continue;
            }
            this.delete(task.getId(), operatorId);
        }
    }

    @Override
    public void createQuartzJob(Long taskId, Long operatorId) throws ChameleonException {
        ChameleonTask task = chameleonTaskService.queryTaskById(taskId);
        Long templateId = task.getTemplateId();
        ChameleonTemplate template = chameleonTemplateService.queryTemplateById(templateId);
        SpiderTemplate spiderTemplate = JsonUtils.jsonToPojo(template.getTemplateConfig(), SpiderTemplate.class);

        Map<String, Object> data = new HashMap<>();
        data.put("spiderTemplate", spiderTemplate);
        data.put("spiderService", this);
        data.put("taskId", taskId);
        quartzManager.addJob(task.getJobGroup() + "-" + task.getId(),
                task.getJobGroup(),
                String.valueOf(task.getRepeatInterval() + "-" + task.getId() + QUARTZ_TRIGGER_NAME_SUFFIX),
                task.getJobGroup(),
                SpiderJob.class,
                data,
                task.getRepeatInterval().intValue(),
                task.getExpression());
    }

    /**
     * 移除任务
     *
     * @param taskId
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void removeQuartzJob(Long taskId, Long operatorId) throws ChameleonException {
        ChameleonTask task = chameleonTaskService.queryTaskById(taskId);
        quartzManager.removeJob(JobKey.jobKey(task.getJobGroup() + "-" + task.getId(), task.getJobGroup()));
    }

    /**
     * 暂停任务
     *
     * @param taskId
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void pauseQuartzJob(Long taskId, Long operatorId) throws ChameleonException {
        ChameleonTask task = chameleonTaskService.queryTaskById(taskId);
        quartzManager.pauseJob(JobKey.jobKey(task.getJobGroup() + "-" + task.getId(), task.getJobGroup()));
    }

    /**
     * 恢复任务
     *
     * @param taskId
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void resumeQuartzJob(Long taskId, Long operatorId) throws ChameleonException {
        ChameleonTask task = chameleonTaskService.queryTaskById(taskId);
        quartzManager.resumeJob(JobKey.jobKey(task.getJobGroup() + "-" + task.getId(), task.getJobGroup()));
    }

    /**
     * 检查是否已存在该定时任务
     *
     * @param taskId
     * @return
     * @throws ChameleonException
     */
    @Override
    public boolean checkQuartzJob(Long taskId) throws ChameleonException {
        ChameleonTask task = chameleonTaskService.queryTaskById(taskId);
        Long templateId = task.getTemplateId();
        ChameleonTemplate template = chameleonTemplateService.queryTemplateById(templateId);
        SpiderTemplate spiderTemplate = JsonUtils.jsonToPojo(template.getTemplateConfig(), SpiderTemplate.class);

        Pair<JobDetail, Trigger> pair = quartzManager.findInfo(JobKey.jobKey(task.getJobGroup() + "-" + task.getId(), task.getJobGroup()));

        if (pair == null && spiderTemplate != null) {
            return true;
        }
        return false;
    }

    /**
     * 生成爬虫
     *
     * @param task
     * @param template
     * @return
     */
    public CommonSpider makeSpider(ChameleonTask task, SpiderTemplate template) {
        SchedulerEnum schedulerEnum = SchedulerEnum.getSchedulerEnum(template.getScheduler());
        DownloaderEnum downloaderEnum = DownloaderEnum.getDownloaderEnum(template.getDownloader());
        DuplicateRemoverEnum duplicateRemoverEnum = DuplicateRemoverEnum.getDuplicateRemoverEnum(template.getDuplicateRemover());

        CommonSpider spider = new CommonSpider(new CommonPageProcessor(template), template);

        //填充调度器和去重器
        fillScheduler(spider, schedulerEnum, duplicateRemoverEnum);
        //填充下载器
        fillDownloader(spider, downloaderEnum);
        //填充管道
        fillPipelines(spider, template.getPipelines());

        spider.startUrls(template.getStartUrls());
        spider.thread(template.getThread());
        spider.setUUID(task.getJobGroup() + "-" + task.getId());

        spider.setTaskManager(taskManager);
        spider.setTaskStatisticsManager(taskStatisticsManager);
        return spider;
    }

    public CommonSpider fillScheduler(CommonSpider spider, SchedulerEnum schedulerEnum, DuplicateRemoverEnum duplicateRemoverEnum) {
        switch (schedulerEnum) {
            case QUEUE:
                spider.setScheduler(new QueueScheduler().setDuplicateRemover((DuplicateRemover) duplicateRemoverEnum.getObject()));
                break;
            case PRIORITY_QUEUE:
                spider.setScheduler(new PriorityScheduler().setDuplicateRemover((DuplicateRemover) duplicateRemoverEnum.getObject()));
                break;
            case FILE_CACHE_QUEUE:
                spider.setScheduler(new FileCachQueueSchduler().setDuplicateRemover((DuplicateRemover) duplicateRemoverEnum.getObject()));
                break;
            case REDIS_QUEUE:
                spider.setScheduler(new RedisScheduler().setDuplicateRemover((DuplicateRemover) duplicateRemoverEnum.getObject()));
                break;
            case REDIS_PRIORITY_QUEUE:
                spider.setScheduler(new RedisPriorityScheduler().setDuplicateRemover((DuplicateRemover) duplicateRemoverEnum.getObject()));
                break;
            default:
                spider.setScheduler(new QueueScheduler().setDuplicateRemover((DuplicateRemover) duplicateRemoverEnum.getObject()));
                break;
        }
        return spider;
    }

    public CommonSpider fillDownloader(CommonSpider spider, DownloaderEnum downloaderEnum) {
        switch (downloaderEnum) {
            case HTTP_CLIENT:
                spider.setDownload(new HttpClientDownloader());
                break;
            case PHANTOM_JS:
                spider.setDownload(new PhantomJSDownloader("/Users/liumingyu/data/chameleon/phantomjs-2.1.1-macosx/bin/phantomjs"
                        , "/Users/liumingyu/data/chameleon/crawl.js"));
                break;
            case SELENIUM:
                spider.setDownload(new SeleniumDownloader("Users/data/chameleon/chromedriver"));
                break;
            default:
                spider.setDownload(new HttpClientDownloader());
                break;
        }
        return spider;
    }

    public CommonSpider fillPipelines(CommonSpider spider, List<Integer> pipelines) {
        for (Integer pipeline : pipelines) {
            PipelineEnum pipelineEnum = PipelineEnum.getPipelineEnum(pipeline);
            switch (pipelineEnum) {
                case CONSOLE_PIPELINE:
                    spider.addPipeline(new ConsolePipeline());
                    break;
                case FILE_PIPELINE:
                    spider.addPipeline(new FilePipeline());
                    break;
                case ES_PIPELINE:
                    spider.addPipeline(new ESPipeline(new ESClient()));
                    break;
                case REDIS_PIPELINE:
                    spider.addPipeline(new RedisPipeline());
                    break;
                default:
                    spider.addPipeline(new ESPipeline(new ESClient()));
                    break;
            }
        }
        return spider;
    }
}
