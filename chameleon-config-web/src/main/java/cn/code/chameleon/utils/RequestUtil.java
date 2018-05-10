package cn.code.chameleon.utils;

import cn.code.chameleon.dto.UserDTO;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author liumingyu
 * @create 2018-05-09 上午10:56
 */
public class RequestUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);

    /**
     * 获取当前用户ID
     *
     * @return
     * @throws ChameleonException
     */
    public static Long getCurrentUserId() throws ChameleonException {
        UserDTO currentUser = UserContext.getCurrentUser();
        if (currentUser == null || currentUser.getId() == null) {
            LOGGER.error("未获取到当前用户ID,或当前用户未登录");
            throw new ChameleonException(ResultCodeEnum.NO_FIND_CURRENT_USER);
        }
        return currentUser.getId();
    }

    /**
     * 获取当前用户姓名
     *
     * @return
     * @throws ChameleonException
     */
    public static String getCurrentUserName() throws ChameleonException {
        UserDTO currentUser = UserContext.getCurrentUser();
        if (currentUser == null || currentUser.getName() == null) {
            LOGGER.error("未获取到当前用户姓名,或当前用户未登录");
            throw new ChameleonException(ResultCodeEnum.NO_FIND_CURRENT_USER);
        }
        return currentUser.getName();
    }

    /**
     * 获取当前用户邮箱
     *
     * @return
     * @throws ChameleonException
     */
    public static String getCurrentUserEmail() throws ChameleonException {
        UserDTO currentUser = UserContext.getCurrentUser();
        if (currentUser == null || currentUser.getEmail() == null) {
            LOGGER.error("未获取到当前用户邮箱,或当前用户未登录");
            throw new ChameleonException(ResultCodeEnum.NO_FIND_CURRENT_USER);
        }
        return currentUser.getEmail();
    }

    /**
     * 获取当前用户状态
     *
     * @return
     * @throws ChameleonException
     */
    public static Integer getCurrentUserStatus() throws ChameleonException {
        UserDTO currentUser = UserContext.getCurrentUser();
        if (currentUser == null || currentUser.getStatus() == null) {
            LOGGER.error("未获取到当前用户状态,或当前用户未登录");
            throw new ChameleonException(ResultCodeEnum.NO_FIND_CURRENT_USER);
        }
        return currentUser.getStatus();
    }

    /**
     * 获取当前用户
     *
     * @return
     * @throws ChameleonException
     */
    public static User getCurrentUser() throws ChameleonException {
        UserDTO currentUser = UserContext.getCurrentUser();
        if (currentUser == null || currentUser.getStatus() == null) {
            LOGGER.error("未获取到当前用户,或当前用户未登录");
            throw new ChameleonException(ResultCodeEnum.NO_FIND_CURRENT_USER);
        }
        return ConvertUtil.convertDTO2User(currentUser);
    }

    /**
     * 获取当前用户token
     *
     * @param request
     * @return
     * @throws ChameleonException
     */
    public static String getToken(HttpServletRequest request) throws ChameleonException {
        String token = null;
        Cookie[] cookie = request.getCookies();
        if (cookie == null) {
            return null;
        }
        for (int i = 0; i < cookie.length; i++) {
            Cookie cook = cookie[i];
            if (cook.getName().equals(Constants.TOKEN_COOKIE)) {
                token = cook.getValue();
            }
        }
        if (token == null || "".equals(token)) {
            return null;
        }
        return token;
    }

    /**
     * 获取当前用户IP
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
