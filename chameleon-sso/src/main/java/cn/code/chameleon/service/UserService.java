package cn.code.chameleon.service;

import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liumingyu
 * @create 2018-05-08 下午6:45
 */
public interface UserService {

    boolean checkAccount(String account) throws ChameleonException;

    boolean checkVerifyCode(String account, String code) throws ChameleonException;

    void saveUser(User user, String code) throws ChameleonException;

    void generateVerifyCode(String account) throws ChameleonException;

    void updatePassword(String account, String password, String code) throws ChameleonException;

    String login(String account, String password, HttpServletRequest request, HttpServletResponse response) throws ChameleonException;

    void logout(HttpServletRequest request) throws ChameleonException;

    void logout(String token) throws ChameleonException;

}
