package cn.code.chameleon.quartz;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author liumingyu
 * @create 2018-05-11 下午8:15
 */
@Component
public class QuartzManager {

    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    /**
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     * @param jobClass     任务
     * @param hours        时间设置，参考quartz说明文档
     * @Description: 添加一个定时任务
     * @Title: QuartzManager.java
     */
    public Pair<TriggerKey, JobKey> addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<? extends Job> jobClass, Map<String, Object> data, int hours, String cronExpression) {
        try {
            JobDetail jobDetail = JobBuilder.newJob()
                    .ofType(jobClass)
                    .usingJobData(new JobDataMap(data))
                    // 任务名，任务组，任务执行类
                    .withIdentity(jobName, jobGroupName).build();
            CronScheduleBuilder scheduleBuilder = null;
            Trigger trigger;
            if (cronExpression != null && !"".equals(cronExpression)) {
                scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger()
                        .forJob(jobName, jobGroupName)
                        .withIdentity(triggerName, triggerGroupName)
                        .withSchedule(scheduleBuilder)
                        .build();
            } else {
                // 触发器
                trigger = TriggerBuilder.newTrigger()
                        .forJob(jobName, jobGroupName)
                        .withIdentity(triggerName, triggerGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatHourlyForever(hours))
                        .build();
            }
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
            scheduler.scheduleJob(jobDetail, trigger);
            return Pair.of(trigger.getKey(), jobDetail.getKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Pair<JobDetail, Trigger> findInfo(JobKey jobKey) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            Trigger trigger = scheduler.getTriggersOfJob(jobKey).get(0);
            return Pair.of(jobDetail, trigger);
        } catch (Exception e) {
            return null;
        }
    }

    public Set<JobKey> listAll(String jobGroup) {
        try {
            return scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return Sets.newConcurrentHashSet();
    }

    /**
     * @Description: 移除一个任务
     * @Title: QuartzManager.java
     */
    public void removeJob(JobKey jobKey) {
        try {
            TriggerKey triggerKey = scheduler.getTriggersOfJob(jobKey).get(0).getKey();
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 暂停一个job
     *
     * @param jobKey
     * @throws SchedulerException
     */
    public void pauseJob(JobKey jobKey) {
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复一个job
     *
     * @param jobKey
     */
    public void resumeJob(JobKey jobKey) {
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description:启动所有定时任务
     * @Title: QuartzManager.java
     */
    public void startJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     * @Title: QuartzManager.java
     */
    public void shutdownJobs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
