package cn.code.chameleon.service.impl;

import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.mapper.UserMapper;
import cn.code.chameleon.pojo.User;
import cn.code.chameleon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:11
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void saveUser(User user) throws ChameleonException {

    }

    @Override
    public void updateUser(User user) throws ChameleonException {

    }

    @Override
    public void deleteUserById(Long id) throws ChameleonException {

    }

    @Override
    public User queryUserById(Long id) throws ChameleonException {
        return null;
    }
}
