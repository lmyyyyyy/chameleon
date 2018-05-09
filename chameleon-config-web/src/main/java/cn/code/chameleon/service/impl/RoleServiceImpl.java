package cn.code.chameleon.service.impl;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.mapper.RoleMapper;
import cn.code.chameleon.pojo.Role;
import cn.code.chameleon.pojo.RoleExample;
import cn.code.chameleon.service.FunctionService;
import cn.code.chameleon.service.RoleService;
import cn.code.chameleon.service.UserService;
import cn.code.chameleon.vo.RoleVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:17
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private FunctionService functionService;

    /**
     * 创建角色
     *
     * @param role
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void saveRole(Role role, Long operatorId) throws ChameleonException {
        if (role == null) {
            throw new ChameleonException(ResultCodeEnum.ROLE_DATA_EMPTY);
        }
        if (role.getName() == null || "".equals(role.getName())) {
            throw new ChameleonException(ResultCodeEnum.ROLE_NAME_EMPTY);
        }
        role.setIsDelete(false);
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        roleMapper.insert(role);
    }

    /**
     * 更新角色
     *
     * @param role
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void updateRole(Role role, Long operatorId) throws ChameleonException {
        if (role == null) {
            throw new ChameleonException(ResultCodeEnum.ROLE_DATA_EMPTY);
        }
        if (role.getName() == null || "".equals(role.getName())) {
            throw new ChameleonException(ResultCodeEnum.ROLE_NAME_EMPTY);
        }
        role.setUpdateTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 根据角色ID删除角色,并清除与用户和功能的绑定关系
     *
     * @param roleId
     * @param operatorId
     * @throws ChameleonException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, ChameleonException.class})
    @Override
    public void deleteRoleById(Long roleId, Long operatorId) throws ChameleonException {
        Role role = new Role();
        role.setId(roleId);
        role.setIsDelete(true);
        role.setUpdateTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);
        //删除与用户的绑定关系
        userService.deleteUserRelationRoleByRoleId(roleId, operatorId);
        //删除与功能的绑定关系
        functionService.deleteRoleRelationFunctionByRoleId(roleId, operatorId);
    }

    /**
     * 根据角色ID查询角色信息
     *
     * @param roleId
     * @return
     * @throws ChameleonException
     */
    @Override
    public Role queryRoleById(Long roleId) throws ChameleonException {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    /**
     * 分页查询角色列表
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
    public PageData<RoleVO> pageRoles(String keyword, Integer page, Integer size, String orderField, String orderType) throws ChameleonException {
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
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        if (keyword != null && !"".equals(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        criteria.andIsDeleteEqualTo(false);
        example.setOrderByClause(orderField + " " + orderType);
        List<Role> roles = roleMapper.selectByExample(example);
        if (roles == null || roles.isEmpty()) {
            return new PageData<>(0, new ArrayList<>());
        }
        PageInfo<Role> pageInfo = new PageInfo<>(roles);

        return new PageData<>(pageInfo.getTotal(), this.convertRoles2VOS(roles));
    }

    /**
     * 根据角色ID集合查询角色集合
     *
     * @param roleIds
     * @return
     * @throws ChameleonException
     */
    @Override
    public List<Role> queryRolesByIds(Set<Long> roleIds) throws ChameleonException {
        if (roleIds == null || roleIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Role> roles = Lists.newArrayListWithCapacity(roleIds.size());
        for (Long roleId : roleIds) {
            if (roleId == null || roleId <= 0) {
                continue;
            }
            Role role = this.queryRoleById(roleId);
            if (role == null) {
                continue;
            }
            roles.add(role);
        }
        return roles;
    }

    /**
     * 角色集合转VO集合
     *
     * @param roles
     * @return
     * @throws ChameleonException
     */
    public List<RoleVO> convertRoles2VOS(List<Role> roles) throws ChameleonException {
        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }
        List<RoleVO> vos = Lists.newArrayListWithCapacity(roles.size());
        for (Role role : roles) {
            if (role == null || role.getId() == null) {
                continue;
            }
            RoleVO roleVO = convertRole2VO(role);
            if (roleVO == null) {
                continue;
            }
            vos.add(roleVO);
        }
        return vos;
    }

    /**
     * 角色转VO
     *
     * @param role
     * @return
     * @throws ChameleonException
     */
    public RoleVO convertRole2VO(Role role) throws ChameleonException {
        if (role == null) {
            return null;
        }
        RoleVO roleVO = new RoleVO(role);
        roleVO.setFunctionCount(functionService.countFunctionsByRoleId(role.getId()));
        roleVO.setUserCount(userService.countUsersByRoleId(role.getId()));
        return roleVO;
    }
}
