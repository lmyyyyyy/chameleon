package cn.code.chameleon.quartz;

import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.model.SpiderTemplate;
import cn.code.chameleon.service.SpiderService;
import cn.code.chameleon.utils.RequestUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author liumingyu
 * @create 2018-05-19 下午3:16
 */
@DisallowConcurrentExecution
public class SpiderJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderJob.class);

    private Long taskId;

    private SpiderService spiderService;

    private SpiderTemplate spiderTemplate;

    public SpiderJob setSpiderTemplate(SpiderTemplate spiderTemplate) {
        this.spiderTemplate = spiderTemplate;
        return this;
    }

    public SpiderJob setTaskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public SpiderJob setSpiderService(SpiderService spiderService) {
        this.spiderService = spiderService;
        return this;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Long operatorId = 0L;
        try {
            operatorId = RequestUtil.getCurrentUserId();
        } catch (ChameleonException e) {
            LOGGER.warn("{} 未获取到当前用户ID,默认使用系统ID", "Quartz job", e);
        }
        LOGGER.info("{} spider start! operatorId = {}, taskId = {}", "Quartz job", operatorId, taskId);
        String uuid = "";
        try {
            uuid = spiderService.start(taskId, operatorId);
        } catch (Exception e) {
            LOGGER.error("{} spider start error!", "Quartz job", e);
        }
        LOGGER.info("{} spider end! operatorId = {}, taskId = {}, uuid = {}", "Quartz job", operatorId, taskId, uuid);
    }
}
