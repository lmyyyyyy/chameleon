package cn.code.chameleon.service.impl;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.mapper.ChameleonStatisticsMapper;
import cn.code.chameleon.model.TaskStatisticsManager;
import cn.code.chameleon.pojo.ChameleonStatistics;
import cn.code.chameleon.pojo.ChameleonStatisticsExample;
import cn.code.chameleon.service.ChameleonStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:19
 */
@Service
public class ChameleonStatisticsServiceImpl implements ChameleonStatisticsService {

    @Autowired
    private ChameleonStatisticsMapper chameleonStatisticsMapper;

    @Autowired
    private TaskStatisticsManager taskStatisticsManager;

    /**
     * 保存爬虫统计
     *
     * @param statistics
     * @param operatorId
     * @throws Exception
     */
    @Override
    public void saveStatistic(ChameleonStatistics statistics, Long operatorId) throws ChameleonException {
        if (statistics == null) {
            throw new ChameleonException(ResultCodeEnum.STATISTICS_DATA_EMPTY);
        }
        statistics.setIsDelete(false);
        statistics.setUpdateTime(new Date());
        statistics.setCreateTime(new Date());
        statistics.setCrawlCount(0L);
        statistics.setErrorCount(0L);
        statistics.setRunHours(0L);
        statistics.setStopCount(0);
        chameleonStatisticsMapper.insert(statistics);

    }

    /**
     * 更新爬虫统计
     *
     * @param statistics
     * @param operatorId
     * @throws Exception
     */
    @Override
    public void updateStatistic(ChameleonStatistics statistics, Long operatorId) throws ChameleonException {
        if (statistics == null) {
            throw new ChameleonException(ResultCodeEnum.STATISTICS_DATA_EMPTY);
        }
        statistics.setUpdateTime(new Date());
        chameleonStatisticsMapper.updateByPrimaryKeySelective(statistics);
    }

    /**
     * 更新统计集合
     *
     * @param statisticsList
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void updateStatistics(List<ChameleonStatistics> statisticsList, Long operatorId) throws ChameleonException {
        if (statisticsList == null || statisticsList.isEmpty()) {
            return;
        }
        for (ChameleonStatistics statistics : statisticsList) {
            if (statistics == null) {
                continue;
            }
            this.updateStatistic(statistics, operatorId);
        }
    }

    /**
     * 根据ID删除爬虫统计
     *
     * @param id
     * @param operatorId
     * @throws Exception
     */
    @Override
    public void deleteStatisticById(Long id, Long operatorId) throws ChameleonException {
        ChameleonStatistics statistics = new ChameleonStatistics();
        statistics.setId(id);
        statistics.setIsDelete(true);
        statistics.setUpdateTime(new Date());
        chameleonStatisticsMapper.updateByPrimaryKeySelective(statistics);

    }

    @Override
    public void deleteStatisticByTaskId(Long taskId, Long operatorId) throws ChameleonException {
        ChameleonStatistics statistics = new ChameleonStatistics();
        statistics.setTaskId(taskId);
        statistics.setUpdateTime(new Date());
        statistics.setIsDelete(true);

        ChameleonStatisticsExample example = new ChameleonStatisticsExample();
        ChameleonStatisticsExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(taskId);
        chameleonStatisticsMapper.updateByExampleSelective(statistics, example);
    }

    /**
     * 根据ID查询爬虫统计
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ChameleonStatistics queryStatisticById(Long id) throws ChameleonException {
        ChameleonStatistics statistics = chameleonStatisticsMapper.selectByPrimaryKey(id);
        ChameleonStatistics temp = taskStatisticsManager.getTaskStatisticsById(statistics.getTaskId());
        if (temp == null) {
            taskStatisticsManager.put(statistics.getTaskId(), statistics);
            return statistics;
        }
        return temp;
    }

    /**
     * 分页查询爬虫统计
     *
     * @param taskId
     * @param page
     * @param size
     * @param orderField
     * @param orderType
     * @return
     * @throws Exception
     */
    @Override
    public PageData<ChameleonStatistics> pageStatistics(Long taskId, Integer page, Integer size, String orderField, String orderType) throws ChameleonException {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 20;
        }
        if (orderField == null || "".equals(orderField)) {
            orderField = "create_time";
        }
        if (orderType == null || "".equals(orderType)) {
            orderType = "desc";
        }
        PageHelper.startPage(page, size);
        ChameleonStatisticsExample example = new ChameleonStatisticsExample();
        ChameleonStatisticsExample.Criteria criteria = example.createCriteria();
        if (taskId != null && taskId > 0) {
            criteria.andTaskIdEqualTo(taskId);
        }
        criteria.andIsDeleteEqualTo(false);
        example.setOrderByClause(orderField + " " + orderType);
        List<ChameleonStatistics> statistics = chameleonStatisticsMapper.selectByExample(example);
        PageInfo<ChameleonStatistics> pageInfo = new PageInfo<>(statistics);
        List<ChameleonStatistics> realStatistics = convertStatisticsFromReal(statistics);
        return new PageData<>(pageInfo.getTotal(), realStatistics);
    }

    public List<ChameleonStatistics> convertStatisticsFromReal(List<ChameleonStatistics> statistics) {
        if (statistics == null || statistics.isEmpty()) {
            return new ArrayList<>();
        }
        List<ChameleonStatistics> statisticsList = Lists.newArrayListWithCapacity(statistics.size());
        for (ChameleonStatistics statistic : statistics) {
            if (statistic == null || statistic.getTaskId() == null) {
                continue;
            }
            ChameleonStatistics temp = taskStatisticsManager.getTaskStatisticsById(statistic.getTaskId());
            if (temp == null) {
                taskStatisticsManager.put(statistic.getTaskId(), statistic);
                statisticsList.add(statistic);
                continue;
            }
            statisticsList.add(temp);
        }
        return statisticsList;
    }

    /**
     * 查询爬虫统计列表
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    @Override
    public List<ChameleonStatistics> queryStatistics(Long taskId) throws ChameleonException {
        ChameleonStatisticsExample example = new ChameleonStatisticsExample();
        ChameleonStatisticsExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        if (taskId != null && taskId > 0) {
            criteria.andTaskIdEqualTo(taskId);
        }
        return chameleonStatisticsMapper.selectByExample(example);
    }


}
