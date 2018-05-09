package cn.code.chameleon.service.impl;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.mapper.FunctionMapper;
import cn.code.chameleon.mapper.RoleRelationFunctionMapper;
import cn.code.chameleon.pojo.Function;
import cn.code.chameleon.pojo.FunctionExample;
import cn.code.chameleon.pojo.Role;
import cn.code.chameleon.pojo.RoleRelationFunction;
import cn.code.chameleon.pojo.RoleRelationFunctionExample;
import cn.code.chameleon.service.FunctionService;
import cn.code.chameleon.service.RoleService;
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
import java.util.stream.Collectors;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:17
 */
@Service
public class FunctionServiceImpl implements FunctionService {

    @Autowired
    private FunctionMapper functionMapper;

    @Autowired
    private RoleRelationFunctionMapper roleRelationFunctionMapper;

    @Autowired
    private RoleService roleService;

    /**
     * 检查功能标识有效性 true:有效; false:无效
     *
     * @param code
     * @return
     * @throws ChameleonException
     */
    @Override
    public boolean checkFunctionCode(String code) throws ChameleonException {
        FunctionExample example = new FunctionExample();
        FunctionExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(code);
        criteria.andIsDeleteEqualTo(false);
        List<Function> functions = functionMapper.selectByExample(example);
        if (functions == null || functions.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 创建功能
     *
     * @param function
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void saveFunction(Function function, Long operatorId) throws ChameleonException {
        if (function == null) {
            throw new ChameleonException(ResultCodeEnum.FUNCTION_DATA_EMPTY);
        }
        if (function.getCode() == null || "".equals(function.getCode())) {
            throw new ChameleonException(ResultCodeEnum.FUNCTION_NAME_EMPTY);
        }
        if (!checkFunctionCode(function.getCode())) {
            throw new ChameleonException(ResultCodeEnum.FUNCTION_CODE_EXIST);
        }
        function.setCreateTime(new Date());
        function.setUpdateTime(new Date());
        function.setIsDelete(false);
        functionMapper.insert(function);
    }

    /**
     * 更新功能
     *
     * @param function
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void updateFunction(Function function, Long operatorId) throws ChameleonException {
        if (function == null) {
            throw new ChameleonException(ResultCodeEnum.FUNCTION_DATA_EMPTY);
        }
        function.setUpdateTime(new Date());
        functionMapper.updateByPrimaryKeySelective(function);
    }

    /**
     * 根据ID删除功能并删除与角色的绑定关系
     *
     * @param functionId
     * @param operatorId
     * @throws ChameleonException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, ChameleonException.class})
    @Override
    public void deleteFunctionById(Long functionId, Long operatorId) throws ChameleonException {
        Function function = new Function();
        function.setId(functionId);
        function.setIsDelete(true);
        function.setUpdateTime(new Date());
        functionMapper.updateByPrimaryKeySelective(function);

        this.deleteRoleRelationFunctionByFunctionId(functionId, operatorId);
    }

    /**
     * 根据ID查询功能信息
     *
     * @param functionId
     * @return
     * @throws ChameleonException
     */
    @Override
    public Function queryFunctionById(Long functionId) throws ChameleonException {
        return functionMapper.selectByPrimaryKey(functionId);
    }

    /**
     * 分页查询功能列表
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
    public PageData<Function> pageFunctions(String keyword, Integer page, Integer size, String orderField, String orderType) throws ChameleonException {
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
        FunctionExample example = new FunctionExample();
        FunctionExample.Criteria criteria = example.createCriteria();

        if (keyword != null && !"".equals(keyword)) {
            criteria.andCodeLike("%" + keyword + "%");
        }
        criteria.andIsDeleteEqualTo(false);
        example.setOrderByClause(orderField + " " + orderType);
        List<Function> functions = functionMapper.selectByExample(example);
        PageInfo<Function> pageInfo = new PageInfo<>(functions);
        return new PageData<>(pageInfo.getTotal(), functions);
    }

    /**
     * 保存角色与功能的绑定关系
     *
     * @param roleId
     * @param functionId
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void saveRoleRelationFunction(Long roleId, Long functionId, Long operatorId) throws ChameleonException {
        RoleRelationFunction roleRelationFunction = new RoleRelationFunction();
        roleRelationFunction.setRoleId(roleId);
        roleRelationFunction.setFunctionId(functionId);
        roleRelationFunction.setCreateTime(new Date());
        roleRelationFunction.setUpdateTime(new Date());
        roleRelationFunction.setOperatorId(operatorId);
        roleRelationFunction.setIsDelete(false);

        roleRelationFunctionMapper.insert(roleRelationFunction);
    }

    /**
     * 根据角色ID和功能ID删除绑定关系
     *
     * @param roleId
     * @param functionId
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void deleteRoleRelationFunction(Long roleId, Long functionId, Long operatorId) throws ChameleonException {
        RoleRelationFunction roleRelationFunction = new RoleRelationFunction();
        roleRelationFunction.setIsDelete(true);
        roleRelationFunction.setRoleId(roleId);
        roleRelationFunction.setFunctionId(functionId);
        roleRelationFunction.setOperatorId(operatorId);
        roleRelationFunction.setUpdateTime(new Date());

        RoleRelationFunctionExample example = new RoleRelationFunctionExample();
        RoleRelationFunctionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andFunctionIdEqualTo(functionId);

        roleRelationFunctionMapper.updateByExampleSelective(roleRelationFunction, example);
    }

    /**
     * 根据角色ID删除关联的功能绑定关系
     *
     * @param roleId
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void deleteRoleRelationFunctionByRoleId(Long roleId, Long operatorId) throws ChameleonException {
        RoleRelationFunction roleRelationFunction = new RoleRelationFunction();
        roleRelationFunction.setIsDelete(true);
        roleRelationFunction.setRoleId(roleId);
        roleRelationFunction.setOperatorId(operatorId);
        roleRelationFunction.setUpdateTime(new Date());

        RoleRelationFunctionExample example = new RoleRelationFunctionExample();
        RoleRelationFunctionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);

        roleRelationFunctionMapper.updateByExampleSelective(roleRelationFunction, example);
    }

    /**
     * 根据功能ID删除关联的角色绑定关系
     *
     * @param functionId
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void deleteRoleRelationFunctionByFunctionId(Long functionId, Long operatorId) throws ChameleonException {
        RoleRelationFunction roleRelationFunction = new RoleRelationFunction();
        roleRelationFunction.setIsDelete(true);
        roleRelationFunction.setFunctionId(functionId);
        roleRelationFunction.setOperatorId(operatorId);
        roleRelationFunction.setUpdateTime(new Date());

        RoleRelationFunctionExample example = new RoleRelationFunctionExample();
        RoleRelationFunctionExample.Criteria criteria = example.createCriteria();
        criteria.andFunctionIdEqualTo(functionId);

        roleRelationFunctionMapper.updateByExampleSelective(roleRelationFunction, example);
    }

    /**
     * 根据功能ID集合查询功能集合
     *
     * @param functionIds
     * @return
     * @throws ChameleonException
     */
    @Override
    public List<Function> queryFunctionsByIds(Set<Long> functionIds) throws ChameleonException {
        if (functionIds == null || functionIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Function> functions = Lists.newArrayListWithCapacity(functionIds.size());
        for (Long functionId : functionIds) {
            if (functionId == null || functionId <= 0) {
                continue;
            }
            Function function = this.queryFunctionById(functionId);
            if (function == null) {
                continue;
            }
            functions.add(function);
        }
        return functions;
    }

    /**
     * 根据角色ID查询绑定的功能数量
     *
     * @param roleId
     * @return
     * @throws ChameleonException
     */
    @Override
    public int countFunctionsByRoleId(Long roleId) throws ChameleonException {
        RoleRelationFunctionExample example = new RoleRelationFunctionExample();
        RoleRelationFunctionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andIsDeleteEqualTo(false);
        return roleRelationFunctionMapper.countByExample(example);
    }

    /**
     * 根据角色ID分页查询绑定的功能列表
     *
     * @param roleId
     * @param page
     * @param size
     * @return
     * @throws ChameleonException
     */
    @Override
    public PageData<Function> pageFunctionsByRoleId(Long roleId, Integer page, Integer size) throws ChameleonException {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 20;
        }
        PageHelper.startPage(page, size);
        RoleRelationFunctionExample example = new RoleRelationFunctionExample();
        RoleRelationFunctionExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        criteria.andRoleIdEqualTo(roleId);
        example.setOrderByClause("create_time desc");
        List<RoleRelationFunction> roleRelationFunctions = roleRelationFunctionMapper.selectByExample(example);
        if (roleRelationFunctions == null || roleRelationFunctions.isEmpty()) {
            return new PageData<>(0, new ArrayList<>());
        }
        PageInfo<RoleRelationFunction> pageInfo = new PageInfo<>(roleRelationFunctions);

        Set<Long> functionIds = roleRelationFunctions.stream().map(roleRelationFunction -> roleRelationFunction.getFunctionId()).collect(Collectors.toSet());

        List<Function> functions = this.queryFunctionsByIds(functionIds);

        return new PageData<>(pageInfo.getTotal(), functions);
    }

    /**
     * 根据功能ID分页查询绑定的角色列表
     *
     * @param functionId
     * @param page
     * @param size
     * @return
     * @throws ChameleonException
     */
    @Override
    public PageData<Role> pageRolesByFunctionId(Long functionId, Integer page, Integer size) throws ChameleonException {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 20;
        }
        PageHelper.startPage(page, size);
        RoleRelationFunctionExample example = new RoleRelationFunctionExample();
        RoleRelationFunctionExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        criteria.andFunctionIdEqualTo(functionId);
        example.setOrderByClause("create_time desc");
        List<RoleRelationFunction> roleRelationFunctions = roleRelationFunctionMapper.selectByExample(example);
        if (roleRelationFunctions == null || roleRelationFunctions.isEmpty()) {
            return new PageData<>(0, new ArrayList<>());
        }
        PageInfo<RoleRelationFunction> pageInfo = new PageInfo<>(roleRelationFunctions);

        Set<Long> roleIds = roleRelationFunctions.stream().map(roleRelationFunction -> roleRelationFunction.getRoleId()).collect(Collectors.toSet());

        List<Role> roles = roleService.queryRolesByIds(roleIds);

        return new PageData<>(pageInfo.getTotal(), roles);
    }
}
