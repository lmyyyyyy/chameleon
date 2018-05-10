package cn.code.chameleon.service;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.Function;
import cn.code.chameleon.pojo.Role;
import cn.code.chameleon.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:11
 */
public interface UserService {

    boolean checkAccount(String account) throws ChameleonException;

    boolean checkAccountAndPassword(String account, String password) throws ChameleonException;

    void saveUser(User user) throws ChameleonException;

    void updateUser(User user) throws ChameleonException;

    void updatePassword(User user, String oldPassword, String newPassword) throws ChameleonException;

    void deleteUserById(Long id, Long operatorId) throws ChameleonException;

    User queryUserById(Long id) throws ChameleonException;

    PageData<User> pageUsers(String keyword, Integer page, Integer size, String orderField, String orderType) throws ChameleonException;

    void logout(HttpServletRequest request) throws ChameleonException;

    void logout(String token) throws ChameleonException;

    void saveUserRelationRole(Long userId, Long roleId, Long operatorId) throws ChameleonException;

    void deleteUserRelationRole(Long userId, Long roleId, Long operatorId) throws ChameleonException;

    void deleteUserRelationRoleByUserId(Long userId, Long operatorId) throws ChameleonException;

    void deleteUserRelationRoleByRoleId(Long roleId, Long operatorId) throws ChameleonException;

    List<User> queryUsersByIds(Set<Long> userIds) throws ChameleonException;

    int countUsersByRoleId(Long roleId) throws ChameleonException;

    PageData<User> pageUsersByRoleId(Long roleId, Integer page, Integer size) throws ChameleonException;

    PageData<Role> pageRolesByUserId(Long userId, Integer page, Integer size) throws ChameleonException;

    Set<Long> queryRoleIdsByUserId(Long userId) throws ChameleonException;

    List<Function> queryFunctionsByUserId(Long userId) throws ChameleonException;

    List<String> queryFunctionCodesByUserId(Long userId) throws ChameleonException;

}
