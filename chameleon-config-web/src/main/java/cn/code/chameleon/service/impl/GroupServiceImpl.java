package cn.code.chameleon.service.impl;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.mapper.GroupMapper;
import cn.code.chameleon.pojo.Group;
import cn.code.chameleon.pojo.GroupExample;
import cn.code.chameleon.service.ChameleonTaskService;
import cn.code.chameleon.service.GroupService;
import cn.code.chameleon.vo.GroupVO;
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
 * @create 2018-05-11 下午8:39
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private ChameleonTaskService chameleonTaskService;

    /**
     * 检查组名称是否唯一 true:唯一; false:不唯一
     *
     * @param name
     * @return
     * @throws ChameleonException
     */
    @Override
    public boolean checkGroupNameUnique(String name) throws ChameleonException {
        if (name == null || "".equals(name)) {
            throw new ChameleonException(ResultCodeEnum.GROUP_NAME_EMPTY);
        }
        GroupExample example = new GroupExample();
        GroupExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<Group> groups = groupMapper.selectByExample(example);
        if (groups == null || groups.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 创建组
     *
     * @param group
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void saveGroup(Group group, Long operatorId) throws ChameleonException {
        if (group == null) {
            throw new ChameleonException(ResultCodeEnum.GROUP_DATA_EMPTY);
        }
        if (!checkGroupNameUnique(group.getName())) {
            throw new ChameleonException(ResultCodeEnum.GROUP_NAME_HAS_EXISTED);
        }
        group.setCreateTime(new Date());
        group.setUpdateTime(new Date());
        group.setIsDelete(false);
        group.setOperatorId(operatorId);
        groupMapper.insert(group);
    }

    /**
     * 更新组
     *
     * @param group
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void updateGroup(Group group, Long operatorId) throws ChameleonException {
        if (group == null) {
            throw new ChameleonException(ResultCodeEnum.GROUP_DATA_EMPTY);
        }
        group.setOperatorId(operatorId);
        group.setUpdateTime(new Date());
        groupMapper.updateByPrimaryKeySelective(group);
    }

    /**
     * 删除组
     *
     * @param id
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void deleteGroupById(Long id, Long operatorId) throws ChameleonException {
        if (chameleonTaskService.countTaskByGroupId(id) > 0) {
            throw new ChameleonException(ResultCodeEnum.GROUP_HAS_BEEN_BIND);
        }
        Group group = new Group();
        group.setId(id);
        group.setIsDelete(true);
        group.setOperatorId(operatorId);
        group.setUpdateTime(new Date());
        groupMapper.updateByPrimaryKeySelective(group);
    }

    /**
     * 查询组信息
     *
     * @param id
     * @return
     * @throws ChameleonException
     */
    @Override
    public Group queryGroupById(Long id) throws ChameleonException {
        return groupMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询组列表
     *
     * @param keyword
     * @param page
     * @param size
     * @param orderField
     * @param orderType
     * @return
     * @throws ChameleonException
     */
    @Override
    public PageData<GroupVO> pageGroups(String keyword, Integer page, Integer size, String orderField, String orderType) throws ChameleonException {
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
        GroupExample example = new GroupExample();
        GroupExample.Criteria criteria = example.createCriteria();
        if (keyword != null && !"".equals(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        criteria.andIsDeleteEqualTo(false);
        example.setOrderByClause(orderField + " " + orderType);
        List<Group> groups = groupMapper.selectByExample(example);
        PageInfo<Group> pageInfo = new PageInfo<>(groups);
        List<GroupVO> groupVOS = this.convertGroups2VOS(groups);
        return new PageData<>(pageInfo.getTotal(), groupVOS);
    }

    private List<GroupVO> convertGroups2VOS(List<Group> groups) throws ChameleonException {
        if (groups == null || groups.isEmpty()) {
            return new ArrayList<>();
        }
        List<GroupVO> vos = Lists.newArrayListWithCapacity(groups.size());
        for (Group group : groups) {
            if (group == null) {
                continue;
            }
            GroupVO vo = this.convertGroup2VO(group);
            if (vo == null) {
                continue;
            }
            vos.add(vo);
        }
        return vos;
    }

    private GroupVO convertGroup2VO(Group group) throws ChameleonException {
        if (group == null) {
            return null;
        }
        GroupVO groupVO = new GroupVO(group);
        groupVO.setTaskCount(chameleonTaskService.countTaskByGroupId(group.getId()));
        return groupVO;
    }
}
