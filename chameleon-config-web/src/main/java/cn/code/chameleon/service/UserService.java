package cn.code.chameleon.service;

import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.User;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:11
 */
public interface UserService {

    void saveUser(User user) throws ChameleonException;

    void updateUser(User user) throws ChameleonException;

    void deleteUserById(Long id) throws ChameleonException;

    User queryUserById(Long id) throws ChameleonException;
}
