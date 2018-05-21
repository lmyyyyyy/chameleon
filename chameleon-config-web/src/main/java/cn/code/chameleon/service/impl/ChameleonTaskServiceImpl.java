package cn.code.chameleon.service.impl;

import cn.code.chameleon.Spider;
import cn.code.chameleon.common.PageData;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.mapper.ChameleonTaskMapper;
import cn.code.chameleon.model.SpiderManager;
import cn.code.chameleon.model.TaskManager;
import cn.code.chameleon.model.TaskStatisticsManager;
import cn.code.chameleon.pojo.ChameleonStatistics;
import cn.code.chameleon.pojo.ChameleonTask;
import cn.code.chameleon.pojo.ChameleonTaskExample;
import cn.code.chameleon.pojo.ChameleonTemplate;
import cn.code.chameleon.pojo.Group;
import cn.code.chameleon.service.ChameleonStatisticsService;
import cn.code.chameleon.service.ChameleonTaskService;
import cn.code.chameleon.service.ChameleonTemplateService;
import cn.code.chameleon.service.GroupService;
import cn.code.chameleon.spider.CommonSpider;
import cn.code.chameleon.vo.SpiderVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:18
 */
@Service
public class ChameleonTaskServiceImpl implements ChameleonTaskService {

    @Autowired
    private ChameleonTaskMapper chameleonTaskMapper;

    @Autowired
    private ChameleonTemplateService chameleonTemplateService;

    @Autowired
    private ChameleonStatisticsService chameleonStatisticsService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private SpiderManager spiderManager;

    @Autowired
    private TaskStatisticsManager taskStatisticsManager;

    /**
     * 绑定了该模版的任务数量
     *
     * @param templateId
     * @return
     * @throws ChameleonException
     */
    @Override
    public int countTaskByTemplateId(Long templateId) throws ChameleonException {
        ChameleonTaskExample example = new ChameleonTaskExample();
        ChameleonTaskExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        criteria.andTemplateIdEqualTo(templateId);
        return chameleonTaskMapper.countByExample(example);
    }

    /**
     * 绑定了该组的任务数量
     *
     * @param groupId
     * @return
     * @throws ChameleonException
     */
    @Override
    public int countTaskByGroupId(Long groupId) throws ChameleonException {
        ChameleonTaskExample example = new ChameleonTaskExample();
        ChameleonTaskExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        criteria.andGroupIdEqualTo(groupId);
        return chameleonTaskMapper.countByExample(example);
    }

    /**
     * 创建爬虫任务
     *
     * @param task
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void saveTask(ChameleonTask task, Long operatorId) throws ChameleonException {
        validateTask(task);
        task.setIsDelete(false);
        task.setStatus(Spider.Status.Stopped.getValue());
        task.setCreateTime(new Date());
        task.setUpdateTime(new Date());
        task.setOperatorId(operatorId);
        chameleonTaskMapper.insert(task);
        taskManager.put(task.getJobGroup() + "-" + task.getId(), task);

        //创建爬虫统计
        ChameleonStatistics statistics = new ChameleonStatistics();
        statistics.setTaskId(task.getId());
        chameleonStatisticsService.saveStatistic(statistics, operatorId);
        taskStatisticsManager.put(task.getId(), statistics);
    }

    /**
     * 更新任务
     *
     * @param task
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void updateTask(ChameleonTask task, Long operatorId) throws ChameleonException {
        validateTask(task);
        task.setUpdateTime(new Date());
        task.setOperatorId(operatorId);
        chameleonTaskMapper.updateByPrimaryKey(task);
    }

    /**
     * 更新任务状态
     *
     * @param taskId
     * @param status
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void updateTaskStatus(Long taskId, Integer status, Long operatorId) throws ChameleonException {
        ChameleonTask task = new ChameleonTask();
        task.setId(taskId);
        task.setStatus(status);
        task.setUpdateTime(new Date());
        task.setOperatorId(operatorId);
        chameleonTaskMapper.updateByPrimaryKeySelective(task);
    }

    /**
     * 根据ID删除爬虫任务
     *
     * @param id
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void deleteTaskById(Long id, Long operatorId) throws ChameleonException {
        ChameleonTask task = this.queryTaskById(id);
        if (task.getStatus().equals(Spider.Status.Running.getValue())) {
            throw new ChameleonException(ResultCodeEnum.TASK_IS_RUNNING);
        }
        task.setIsDelete(true);
        task.setUpdateTime(new Date());
        task.setOperatorId(operatorId);
        chameleonTaskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public ChameleonTask queryTaskById(Long id) throws ChameleonException {
        return chameleonTaskMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据 组 or 模版 or 状态 分页查询爬虫任务列表
     *
     * @param groupId
     * @param templateId
     * @param status
     * @param page
     * @param size
     * @param orderField
     * @param orderType
     * @return
     * @throws ChameleonException
     */
    @Override
    public PageData<ChameleonTask> pageTasks(Long groupId, Long templateId, Integer status, Integer page, Integer size, String orderField, String orderType) throws ChameleonException {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 20;
        }
        if (orderField == null || "".equals(orderField)) {
            orderField = "update_time";
        }
        if (orderType == null || "".equals(orderType)) {
            orderType = "desc";
        }
        PageHelper.startPage(page, size);
        ChameleonTaskExample example = new ChameleonTaskExample();
        ChameleonTaskExample.Criteria criteria = example.createCriteria();
        if (groupId != null && groupId > 0) {
            criteria.andGroupIdEqualTo(groupId);
        }
        if (templateId != null && templateId > 0) {
            criteria.andTemplateIdEqualTo(templateId);
        }
        if (status != null && status >= 0) {
            criteria.andStatusEqualTo(status);
        }
        example.setOrderByClause(orderField + " " + orderType);
        List<ChameleonTask> tasks = chameleonTaskMapper.selectByExample(example);
        PageInfo<ChameleonTask> pageInfo = new PageInfo<>(tasks);
        return new PageData<>(pageInfo.getTotal(), tasks);
    }

    /**
     * 分页查询爬虫列表
     *
     * @param groupId
     * @param templateId
     * @param status
     * @param page
     * @param size
     * @param orderField
     * @param orderType
     * @return
     * @throws ChameleonException
     */
    @Override
    public PageData<SpiderVO> pageSpiders(Long groupId, Long templateId, Integer status, Integer page, Integer size, String orderField, String orderType) throws ChameleonException {
        PageData<ChameleonTask> pageData = this.pageTasks(groupId, templateId, status, page, size, orderField, orderType);
        if (pageData.getTotalCount() == 0 || pageData.getItems().isEmpty()) {
            return new PageData<>(0, new ArrayList<>());
        }
        List<ChameleonTask> tasks = pageData.getItems();
        List<SpiderVO> spiderVOS = convertTasks2SpiderVOS(tasks);
        return new PageData<>(pageData.getTotalCount(), spiderVOS);
    }

    private List<SpiderVO> convertTasks2SpiderVOS(List<ChameleonTask> tasks) throws ChameleonException {
        if (tasks == null || tasks.isEmpty()) {
            return new ArrayList<>();
        }
        List<SpiderVO> spiderVOS = Lists.newArrayListWithCapacity(tasks.size());
        for (ChameleonTask task : tasks) {
            if (task == null) {
                continue;
            }
            SpiderVO spiderVO = convertTask2SpiderVO(task);
            if (spiderVO == null) {
                continue;
            }
            spiderVOS.add(spiderVO);
        }
        return spiderVOS;
    }

    private SpiderVO convertTask2SpiderVO(ChameleonTask task) throws ChameleonException {
        if (task == null) {
            return null;
        }
        SpiderVO spiderVO = new SpiderVO();
        spiderVO.setGroupId(task.getGroupId());
        spiderVO.setTaskId(task.getId());
        spiderVO.setTemplateId(task.getTemplateId());
        spiderVO.setUuid(task.getJobGroup() + "-" + task.getId());
        ChameleonTemplate template = chameleonTemplateService.queryTemplateById(task.getTemplateId());
        if (template != null) {
            spiderVO.setDomain(template.getDomain());
        }
        CommonSpider commonSpider = spiderManager.getSpiderById(spiderVO.getUuid());
        if (commonSpider != null) {
            spiderVO.setPageCount(commonSpider.getPageCount());
            spiderVO.setStartTime(commonSpider.getStartTime());
            spiderVO.setStatus(commonSpider.getStatus().getValue());
            spiderVO.setTreadAlive(commonSpider.getThreadAlive());
        }
        return spiderVO;
    }

    /**
     * 验证爬虫任务信息
     *
     * @param task
     * @throws ChameleonException
     */
    @Override
    public void validateTask(ChameleonTask task) throws ChameleonException {
        if (task == null) {
            throw new ChameleonException(ResultCodeEnum.TASK_DATA_EMPTY);
        }
        if (task.getTemplateId() == null) {
            throw new ChameleonException(ResultCodeEnum.TASK_TEMPLATE_EMPTY);
        }
        ChameleonTemplate template = chameleonTemplateService.queryTemplateById(task.getTemplateId());
        if (template == null) {
            throw new ChameleonException(ResultCodeEnum.TASK_TEMPLATE_EMPTY);
        }
        if (task.getGroupId() == null) {
            throw new ChameleonException(ResultCodeEnum.TASK_GROUP_EMPTY);
        }
        Group group = groupService.queryGroupById(task.getGroupId());
        if (group == null) {
            throw new ChameleonException(ResultCodeEnum.TASK_GROUP_EMPTY);
        }
        task.setTriggerGroup(template.getDomain());
        task.setTriggerName(template.getDomain());
        task.setJobName(group.getName());
        task.setJobGroup(group.getName());
    }

    /**
     * 查询所有未被删除的任务
     *
     * @return
     * @throws ChameleonException
     */
    @Override
    public List<ChameleonTask> queryTasks() throws ChameleonException {
        ChameleonTaskExample example = new ChameleonTaskExample();
        ChameleonTaskExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        return chameleonTaskMapper.selectByExample(example);
    }

    /**
     * 根据组ID查询任务ID
     *
     * @param groupId
     * @return
     * @throws ChameleonException
     */
    @Override
    public List<Long> queryTaskIdsByGroupId(Long groupId) throws ChameleonException {
        ChameleonTaskExample example = new ChameleonTaskExample();
        ChameleonTaskExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        criteria.andGroupIdEqualTo(groupId);
        List<ChameleonTask> tasks = chameleonTaskMapper.selectByExample(example);
        List<Long> taskIds = tasks.stream().map(task -> task.getId()).collect(Collectors.toList());
        return taskIds;
    }

}
