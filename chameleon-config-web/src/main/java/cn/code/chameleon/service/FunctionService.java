package cn.code.chameleon.service;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.Function;
import cn.code.chameleon.pojo.Role;

import java.util.List;
import java.util.Set;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:15
 */
public interface FunctionService {

    boolean checkFunctionCode(String code) throws ChameleonException;

    void saveFunction(Function function, Long operatorId) throws ChameleonException;

    void updateFunction(Function function, Long operatorId) throws ChameleonException;

    void deleteFunctionById(Long functionId, Long operatorId) throws ChameleonException;

    Function queryFunctionById(Long functionId) throws ChameleonException;

    PageData<Function> pageFunctions(String keyword, Integer page, Integer size, String orderField, String orderType) throws ChameleonException;

    void saveRoleRelationFunction(Long roleId, Long functionId, Long operatorId) throws ChameleonException;

    void deleteRoleRelationFunction(Long roleId, Long functionId, Long operatorId) throws ChameleonException;

    void deleteRoleRelationFunctionByRoleId(Long roleId, Long operatorId) throws ChameleonException;

    void deleteRoleRelationFunctionByFunctionId(Long functionId, Long operatorId) throws ChameleonException;

    List<Function> queryFunctionsByIds(Set<Long> functionIds) throws ChameleonException;

    int countFunctionsByRoleId(Long roleId) throws ChameleonException;

    PageData<Function> pageFunctionsByRoleId(Long roleId, Integer page, Integer size) throws ChameleonException;

    PageData<Role> pageRolesByFunctionId(Long functionId, Integer page, Integer size) throws ChameleonException;
}
