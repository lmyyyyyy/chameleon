package cn.code.chameleon.controller;

import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.User;
import cn.code.chameleon.service.UserService;
import cn.code.chameleon.utils.RequestUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:33
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final String LOG_PREFIX = "[用户模块] ";

    @Autowired
    private UserService userService;

    /**
     * 分页查询用户列表
     *
     * @param keyword
     * @param page
     * @param size
     * @param orderField
     * @param orderType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询用户列表(刘明宇)", notes = "分页查询用户列表", response = UnifiedResponse.class)
    public UnifiedResponse pageUsers(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                     @RequestParam(value = "orderField", required = false, defaultValue = "create_time") String orderField,
                                     @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderType) throws Exception {
        LOGGER.info("{} 分页查询用户列表 keyword = {}, page = {}, size = {}", LOG_PREFIX, keyword, page, size);
        return new UnifiedResponse(userService.pageUsers(keyword, page, size, orderField, orderType));
    }

    /**
     * 更新用户
     *
     * @param user
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户(刘明宇)", notes = "更新用户", response = UnifiedResponse.class)
    public UnifiedResponse updateUser(@RequestBody User user, HttpServletRequest request) throws Exception {
        LOGGER.info("{} 更新用户 user = {}", LOG_PREFIX, user);
        try {
            userService.updateUser(user);
        } catch (ChameleonException e) {
            LOGGER.error("{} 更新用户 user = {} 失败", LOG_PREFIX, user, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 删除用户
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户(刘明宇)", notes = "删除用户", response = UnifiedResponse.class)
    public UnifiedResponse deleteUserById(@PathVariable("id") Long id, HttpServletRequest request) {
        LOGGER.info("{} 删除用户id = {}", LOG_PREFIX, id);
        Long operatorId = RequestUtil.getCurrentUserId();
        try {
            userService.deleteUserById(id, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 删除用户 id = {} 失败", LOG_PREFIX, operatorId, id, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户ID查询用户信息(刘明宇)", notes = "根据用户ID查询用户信息", response = UnifiedResponse.class)
    public UnifiedResponse queryUserById(@PathVariable("id") Long id) throws Exception {
        LOGGER.info("{} 根据用户ID = {} 查询用户信息", LOG_PREFIX, id);
        return new UnifiedResponse(userService.queryUserById(id));
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logout", method = RequestMethod.PUT)
    @ApiOperation(value = "用户注销(刘明宇)", notes = "用户注销", response = UnifiedResponse.class)
    public UnifiedResponse logout(HttpServletRequest request) throws Exception {
        LOGGER.info("{} 用户注销");
        try {
            userService.logout(request);
        } catch (ChameleonException e) {
            LOGGER.error("{} 用户注销失败", LOG_PREFIX, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 根据用户ID删除与之绑定的角色
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/roles", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据用户ID删除与之绑定的角色(刘明宇)", notes = "根据用户ID删除与之绑定的角色", response = UnifiedResponse.class)
    public UnifiedResponse deleteRolesByUserId(@PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} 根据用户ID = {} 删除与之绑定的角色", LOG_PREFIX, id);
        try {
            userService.deleteUserRelationRoleByUserId(id, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} 根据用户ID = {} 删除与之绑定的角色失败", LOG_PREFIX, id, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }


}
