package cn.code.chameleon.service;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.ChameleonStatistics;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:17
 */
public interface ChameleonStatisticsService {

    void saveStatistic(ChameleonStatistics statistics, Long operatorId) throws ChameleonException;

    void updateStatistic(ChameleonStatistics statistics, Long operatorId) throws ChameleonException;

    void updateStatistics(List<ChameleonStatistics> statisticsList, Long operatorId) throws ChameleonException;

    void deleteStatisticById(Long id, Long operatorId) throws ChameleonException;

    void deleteStatisticByTaskId(Long taskId, Long operatorId) throws ChameleonException;

    ChameleonStatistics queryStatisticById(Long id) throws ChameleonException;

    PageData<ChameleonStatistics> pageStatistics(Long taskId, Integer page, Integer size, String orderField, String orderType) throws ChameleonException;

    List<ChameleonStatistics> queryStatistics(Long taskId) throws ChameleonException;
}
