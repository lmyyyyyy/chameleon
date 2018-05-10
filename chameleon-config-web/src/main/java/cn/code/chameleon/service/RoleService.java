package cn.code.chameleon.service;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.Function;
import cn.code.chameleon.pojo.Role;
import cn.code.chameleon.vo.RoleVO;

import java.util.List;
import java.util.Set;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:15
 */
public interface RoleService {

    void saveRole(Role role, Long operatorId) throws ChameleonException;

    void updateRole(Role role, Long operatorId) throws ChameleonException;

    void deleteRoleById(Long roleId, Long operatorId) throws ChameleonException;

    Role queryRoleById(Long roleId) throws ChameleonException;

    PageData<RoleVO> pageRoles(String keyword, Integer page, Integer size, String orderField, String orderType) throws ChameleonException;

    List<Role> queryRolesByIds(Set<Long> roleIds) throws ChameleonException;

    List<Function> queryFunctionsByRoleIds(Set<Long> roleIds) throws ChameleonException;

}
