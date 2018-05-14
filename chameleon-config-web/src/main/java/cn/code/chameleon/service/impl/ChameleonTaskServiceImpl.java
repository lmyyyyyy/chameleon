package cn.code.chameleon.service.impl;

import cn.code.chameleon.Spider;
import cn.code.chameleon.common.PageData;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.mapper.ChameleonTaskMapper;
import cn.code.chameleon.pojo.ChameleonTask;
import cn.code.chameleon.pojo.ChameleonTaskExample;
import cn.code.chameleon.pojo.ChameleonTemplate;
import cn.code.chameleon.pojo.Group;
import cn.code.chameleon.service.ChameleonTaskService;
import cn.code.chameleon.service.ChameleonTemplateService;
import cn.code.chameleon.service.GroupService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    private GroupService groupService;

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
    }

    /**
     * 更新任务
     *
     * @param task
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void udpateTask(ChameleonTask task, Long operatorId) throws ChameleonException {
        validateTask(task);
        task.setUpdateTime(new Date());
        task.setOperatorId(operatorId);
        chameleonTaskMapper.updateByPrimaryKey(task);
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
}
