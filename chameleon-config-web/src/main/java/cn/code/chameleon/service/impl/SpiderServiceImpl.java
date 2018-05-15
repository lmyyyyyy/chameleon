package cn.code.chameleon.service.impl;

import cn.code.chameleon.Spider;
import cn.code.chameleon.downloader.HttpClientDownloader;
import cn.code.chameleon.downloader.PhantomJSDownloader;
import cn.code.chameleon.downloader.SeleniumDownloader;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.model.SpiderManager;
import cn.code.chameleon.model.SpiderTemplate;
import cn.code.chameleon.model.TaskManager;
import cn.code.chameleon.pipeline.ConsolePipeline;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.pojo.ChameleonTask;
import cn.code.chameleon.pojo.ChameleonTemplate;
import cn.code.chameleon.scheduler.FileCachQueueSchduler;
import cn.code.chameleon.scheduler.PriorityScheduler;
import cn.code.chameleon.scheduler.QueueScheduler;
import cn.code.chameleon.scheduler.component.DuplicateRemover;
import cn.code.chameleon.service.ChameleonTaskService;
import cn.code.chameleon.service.ChameleonTemplateService;
import cn.code.chameleon.service.SpiderService;
import cn.code.chameleon.spider.CommonSpider;
import cn.code.chameleon.spider.enums.DownloaderEnum;
import cn.code.chameleon.spider.enums.DuplicateRemoverEnum;
import cn.code.chameleon.spider.enums.PipelineEnum;
import cn.code.chameleon.spider.enums.SchedulerEnum;
import cn.code.chameleon.spider.pipeline.ESPipeline;
import cn.code.chameleon.spider.pipeline.RedisPipeline;
import cn.code.chameleon.spider.processor.CommonPageProcessor;
import cn.code.chameleon.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-05-12 下午1:57
 */
@Service
public class SpiderServiceImpl implements SpiderService {

    @Autowired
    private SpiderManager spiderManager;

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private ChameleonTaskService chameleonTaskService;

    @Autowired
    private ChameleonTemplateService chameleonTemplateService;

    @Override
    public String start(Long taskId, Long operatorId) throws ChameleonException {
        ChameleonTask task = chameleonTaskService.queryTaskById(taskId);
        Long templateId = task.getTemplateId();
        ChameleonTemplate template = chameleonTemplateService.queryTemplateById(templateId);
        SpiderTemplate spiderTemplate = JsonUtils.jsonToPojo(template.getTemplateConfig(), SpiderTemplate.class);

        CommonSpider spider = makeSpider(task, spiderTemplate);

        spiderManager.put(spider.getUUID(), spider);

        spider.start();

        //改变任务状态
        taskManager.getTaskById(spider.getUUID()).setStatus(Spider.Status.Running.getValue());
        //todo 入库
        return spider.getUUID();
    }

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
                break;
            case REDIS_PRIORITY_QUEUE:
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
                    spider.addPipeline(new ESPipeline());
                    break;
                case REDIS_PIPELINE:
                    spider.addPipeline(new RedisPipeline());
                    break;
                default:
                    spider.addPipeline(new ESPipeline());
                    break;
            }
        }
        return spider;
    }
}
