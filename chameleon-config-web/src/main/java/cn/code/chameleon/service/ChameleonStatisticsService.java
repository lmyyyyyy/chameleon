package cn.code.chameleon.service;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.pojo.ChameleonStatistics;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:17
 */
public interface ChameleonStatisticsService {

    void saveStatistic(ChameleonStatistics statistics, Long operatorId) throws Exception;

    void updateStatistic(ChameleonStatistics statistics, Long operatorId) throws Exception;

    void deleteStatisticById(Long id, Long operatorId) throws Exception;

    ChameleonStatistics queryStatisticById(Long id) throws Exception;

    PageData<ChameleonStatistics> pageStatistics(Long taskId, Integer page, Integer size, String orderField, String orderType) throws Exception;

    List<ChameleonStatistics> queryStatistics(Long taskId) throws Exception;
}
