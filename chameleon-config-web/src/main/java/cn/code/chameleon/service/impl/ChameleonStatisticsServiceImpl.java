package cn.code.chameleon.service.impl;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.mapper.ChameleonStatisticsMapper;
import cn.code.chameleon.pojo.ChameleonStatistics;
import cn.code.chameleon.pojo.ChameleonStatisticsExample;
import cn.code.chameleon.service.ChameleonStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 保存爬虫统计
     *
     * @param statistics
     * @param operatorId
     * @throws Exception
     */
    @Override
    public void saveStatistic(ChameleonStatistics statistics, Long operatorId) throws Exception {
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
    public void updateStatistic(ChameleonStatistics statistics, Long operatorId) throws Exception {
        if (statistics == null) {
            throw new ChameleonException(ResultCodeEnum.STATISTICS_DATA_EMPTY);
        }
        statistics.setUpdateTime(new Date());
        chameleonStatisticsMapper.updateByPrimaryKeySelective(statistics);
    }

    /**
     * 根据ID删除爬虫统计
     *
     * @param id
     * @param operatorId
     * @throws Exception
     */
    @Override
    public void deleteStatisticById(Long id, Long operatorId) throws Exception {
        ChameleonStatistics statistics = new ChameleonStatistics();
        statistics.setId(id);
        statistics.setIsDelete(true);
        statistics.setUpdateTime(new Date());
        chameleonStatisticsMapper.updateByPrimaryKeySelective(statistics);
    }

    /**
     * 根据ID查询爬虫统计
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ChameleonStatistics queryStatisticById(Long id) throws Exception {
        return chameleonStatisticsMapper.selectByPrimaryKey(id);
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
    public PageData<ChameleonStatistics> pageStatistics(Long taskId, Integer page, Integer size, String orderField, String orderType) throws Exception {
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
        return new PageData<>(pageInfo.getTotal(), statistics);
    }

    /**
     * 查询爬虫统计列表
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    @Override
    public List<ChameleonStatistics> queryStatistics(Long taskId) throws Exception {
        ChameleonStatisticsExample example = new ChameleonStatisticsExample();
        ChameleonStatisticsExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        if (taskId != null && taskId > 0) {
            criteria.andTaskIdEqualTo(taskId);
        }
        return chameleonStatisticsMapper.selectByExample(example);
    }


}
