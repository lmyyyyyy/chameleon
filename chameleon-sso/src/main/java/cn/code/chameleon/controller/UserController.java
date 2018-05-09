package cn.code.chameleon.controller;

import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.User;
import cn.code.chameleon.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liumingyu
 * @create 2018-05-09 下午12:19
 */
@RestController
@RequestMapping(value = "/index")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final String LOG_PREFIX = "[注册登录模块] ";

    @Autowired
    private UserService userService;

    /**
     * 验证账号有效性 true:可用; false:不可用
     *
     * @param email
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    @ApiOperation(value = "验证账号有效性(刘明宇)", notes = "验证账号有效性", response = UnifiedResponse.class)
    public UnifiedResponse checkAccount(@RequestParam("email") String email) throws Exception {
        LOGGER.info("{} 验证账号 {} 有效性", LOG_PREFIX, email);
        return new UnifiedResponse(userService.checkAccount(email));
    }

    /**
     * 检查验证码有效性 true:有效; false:无效
     *
     * @param email
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    @ApiOperation(value = "检查验证码有效性(刘明宇)", notes = "检查验证码有效性", response = UnifiedResponse.class)
    public UnifiedResponse checkVerifyCode(@RequestParam("email") String email, @RequestParam("code") String code) throws Exception {
        LOGGER.info("{} 检查账号 {} 的验证码 {} 有效性", LOG_PREFIX, email, code);
        return new UnifiedResponse(userService.checkVerifyCode(email, code));
    }

    /**
     * 获取验证码
     *
     * @param email
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/code", method = RequestMethod.POST)
    @ApiOperation(value = "获取验证码(刘明宇)", notes = "获取验证码", response = UnifiedResponse.class)
    public UnifiedResponse generateVerifyCode(@RequestParam("email") String email) throws Exception {
        LOGGER.info("{} 获取账号 {} 验证码", LOG_PREFIX, email);
        try {
            userService.generateVerifyCode(email);
        } catch (ChameleonException e) {
            LOGGER.error("{} 获取账号 {} 验证码失败 {}", LOG_PREFIX, email, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 注册用户
     *
     * @param user
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ApiOperation(value = "注册用户(刘明宇)", notes = "注册用户", response = UnifiedResponse.class)
    public UnifiedResponse saveUser(@RequestBody User user, @RequestParam String code) throws Exception {
        LOGGER.info("{} 注册用户: {}, 验证码: {}", LOG_PREFIX, user, code);
        try {
            userService.saveUser(user, code);
        } catch (ChameleonException e) {
            LOGGER.error("{} 注册用户: {}, 验证码: {} 失败 {}", LOG_PREFIX, user, code, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 更改密码
     *
     * @param email
     * @param password
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    @ApiOperation(value = "更改密码(刘明宇)", notes = "更改密码", response = UnifiedResponse.class)
    public UnifiedResponse updatePassword(@RequestParam("email") String email,
                                          @RequestParam("password") String password,
                                          @RequestParam("code") String code) throws Exception {
        LOGGER.error("{} 账号: {} 更改密码: {} 验证码: {}", LOG_PREFIX, email, password, code);
        try {
            userService.updatePassword(email, password, code);
        } catch (ChameleonException e) {
            LOGGER.error("{} 账号: {} 更改密码: {} 失败 验证码: {}", LOG_PREFIX, email, password, code, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 用户登录
     *
     * @param user
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录(刘明宇)", notes = "用户登录", response = UnifiedResponse.class)
    public UnifiedResponse login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (user == null) {
            return new UnifiedResponse(ResultCodeEnum.USER_DATA_EMPTY);
        }
        LOGGER.info("{} 用户 {} 登录", LOG_PREFIX, user);
        String token;
        try {
            token = userService.login(user.getEmail(), user.getPassword(), request, response);
        } catch (ChameleonException e) {
            LOGGER.error("{} 用户 {} 登录失败", LOG_PREFIX, user, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse(token);
    }
}
