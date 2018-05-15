package cn.code.chameleon.interceptor;

import cn.code.chameleon.enums.UserStatusEnum;
import cn.code.chameleon.pojo.User;
import cn.code.chameleon.service.RedisClient;
import cn.code.chameleon.utils.Constants;
import cn.code.chameleon.utils.ConvertUtil;
import cn.code.chameleon.utils.JsonUtils;
import cn.code.chameleon.utils.RegexUtils;
import cn.code.chameleon.utils.RequestUtil;
import cn.code.chameleon.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * @author liumingyu
 * @create 2018-05-10 下午3:40
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

    private static final ThreadLocal<Long> timeLong = new ThreadLocal<>();

    public static final String UTF_8 = "UTF-8";

    private boolean isAccess = false;

    @Autowired
    RedisClient redisClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        timeLong.set(System.currentTimeMillis());
        String token = RequestUtil.getToken(request);
        if (token == null || "".equals(token)) {
            LOGGER.warn("未获取到当前用户,或当前用户未登录");
            forbidden(response, HttpServletResponse.SC_UNAUTHORIZED, "未获取到当前用户,或当前用户未登录");
            return false;
        }
        String json = redisClient.get(Constants.USER_TOKEN_KEY + ":" + token);
        if (json == null || "".equals(json)) {
            LOGGER.warn("未获取到当前用户, 会话已失效");
            forbidden(response, HttpServletResponse.SC_UNAUTHORIZED, "未获取到当前用户, 会话已失效");
            return false;
        }
        if (UserContext.getCurrentUser() == null) {
            User user = JsonUtils.jsonToPojo(json, User.class);
            if (user == null) {
                LOGGER.warn("未获取到当前用户, 会话已失效");
                forbidden(response, HttpServletResponse.SC_UNAUTHORIZED, "未获取到当前用户, 会话已失效");
                return false;
            }
            UserContext.setCurrentUser(ConvertUtil.converUser2DTO(user));
        }
        isAccess = isAccessAllowed(json, request, response, o);
        expire(token);
        return isAccess;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        isAccess = false;
        Long startTime = timeLong.get();
        if (startTime == null) {
            startTime = System.currentTimeMillis();
        }
        Long endTime = System.currentTimeMillis();
        Long costTime = endTime - startTime;
        LOGGER.info("该请求执行时间为:" + costTime + "毫秒");
    }

    /**
     * 用户认证和鉴权
     *
     * @param json
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    protected boolean isAccessAllowed(String json, HttpServletRequest request, HttpServletResponse response, Object mappedValue) {
        try {
            User user = RequestUtil.getCurrentUser();
            if (user == null) {
                LOGGER.warn("未获取到当前用户,或当前用户未登录");
                forbidden(response, HttpServletResponse.SC_UNAUTHORIZED,"未获取到当前用户,或当前用户未登录");
                return false;
            }
            if (user.getStatus().equals(UserStatusEnum.DISABLED.getCode())) {
                LOGGER.warn("当前用户 --> {} 已被禁用, 不能登录系统", user);
                forbidden(response, HttpServletResponse.SC_FORBIDDEN, "当前用户已被禁用, 不能登录系统");
                return false;
            }
            User cacheUser = JsonUtils.jsonToPojo(json, User.class);
            if (!user.getId().equals(cacheUser.getId())) {
                LOGGER.warn("当前用户 {} 与服务端缓存的用户 {} 不匹配", user, cacheUser);
                forbidden(response, HttpServletResponse.SC_UNAUTHORIZED, "当前用户与服务端缓存的用户不匹配");
                return false;
            }
            UserContext.removeCurrentUser();
            UserContext.setCurrentUser(ConvertUtil.converUser2DTO(user));

            LOGGER.info("获取SSO用户信息为{}", user);
        } catch (Exception e) {
            LOGGER.warn("获取SSO用户信息失败", e);
            response.setStatus(500);
            return false;
        }
        return true;
    }

    /**
     * 更新过期时间
     *
     * @param token
     */
    private void expire(String token) {
        if (isAccess) {
            long sso_token_expire = Constants.SSO_TOKEN_EXPIRE;

            String temp = redisClient.get(Constants.SSO_TOKEN_EXPIRE_KEY);
            if (temp != null && !"".equals(temp)) {
                if (RegexUtils.checkDigit(temp)) {
                    sso_token_expire = Long.valueOf(temp);
                }
            }
            redisClient.expire(Constants.USER_TOKEN_KEY + ":" + token,
                    sso_token_expire,
                    TimeUnit.SECONDS);
        }
    }

    /**
     * 没有权限,不能登录系统
     *
     * @param response
     * @param msg
     * @throws IOException
     */
    public static void forbidden(HttpServletResponse response, Integer code, String msg) throws IOException {
        response.setHeader("message", URLEncoder.encode(msg, UTF_8));
        response.setCharacterEncoding(UTF_8);
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "message");
        response.sendError(code, msg);
    }
}
