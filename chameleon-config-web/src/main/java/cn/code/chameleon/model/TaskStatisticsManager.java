package cn.code.chameleon.model;

import cn.code.chameleon.pojo.ChameleonStatistics;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liumingyu
 * @create 2018-05-16 下午1:40
 */
@Component
public class TaskStatisticsManager {

    private Map<Long, ChameleonStatistics> statisticsMap = new ConcurrentHashMap<>();

    public void put(Long key, ChameleonStatistics value) {
        statisticsMap.put(key, value);
    }

    public void putList(List<ChameleonStatistics> statistics) {
        for (ChameleonStatistics statistic : statistics) {
            if (statistic == null || statistic.getTaskId() == null) {
                continue;
            }
            this.put(statistic.getTaskId(), statistic);
        }
    }

    public synchronized void incrCrawl(Long taskId) {
        ChameleonStatistics statistics = getTaskStatisticsById(taskId);
        statistics.setCrawlCount(statistics.getCrawlCount() + 1);
        this.put(taskId, statistics);
    }

    public synchronized void setCrawl(Long taskId, Long pageCount) {
        int count = 0;

        ChameleonStatistics statistics = getTaskStatisticsById(taskId);
        statistics.setCrawlCount(pageCount);
        this.put(taskId, statistics);
    }

    public synchronized void incrError(Long taskId) {
        ChameleonStatistics statistics = getTaskStatisticsById(taskId);
        statistics.setErrorCount(statistics.getErrorCount() + 1);
        this.put(taskId, statistics);
    }

    public synchronized void incrStop(Long taskId) {
        ChameleonStatistics statistics = getTaskStatisticsById(taskId);
        statistics.setStopCount(statistics.getStopCount() + 1);
        this.put(taskId, statistics);
    }

    public ChameleonStatistics getTaskStatisticsById(Long key) {
        return statisticsMap.get(key);
    }

    public Map<Long, ChameleonStatistics> get() {
        return statisticsMap;
    }

    public List<ChameleonStatistics> all() {
        List<ChameleonStatistics> statistics = Lists.newArrayListWithCapacity(statisticsMap.size());
        for (ChameleonStatistics statistic : statisticsMap.values()) {
            statistics.add(statistic);
        }
        return statistics;
    }

    public void remove(Long taskId) {
        statisticsMap.remove(taskId);
    }
}
