package cn.code.chameleon.controller;

import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.Role;
import cn.code.chameleon.service.FunctionService;
import cn.code.chameleon.service.RoleService;
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
@RequestMapping("/api/role")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private static final String LOG_PREFIX = "[角色模块] ";

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FunctionService functionService;

    /**
     * 创建角色
     *
     * @param role
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "创建角色(刘明宇)", notes = "创建角色", response = UnifiedResponse.class)
    public UnifiedResponse saveRole(@RequestBody Role role, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 创建角色 {}", LOG_PREFIX, operatorId, role);
        try {
            roleService.saveRole(role, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 创建角色 {} 失败", LOG_PREFIX, operatorId, role, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 根据角色ID和用户ID保存绑定关系
     *
     * @param id
     * @param uid
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/user/{uid}", method = RequestMethod.POST)
    @ApiOperation(value = "根据角色ID和用户ID保存绑定关系(刘明宇)", notes = "根据角色ID和用户ID保存绑定关系", response = UnifiedResponse.class)
    public UnifiedResponse saveUserRelationRole(@PathVariable("id") Long id, @PathVariable("uid") Long uid, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 根据角色ID = {} 和用户ID = {} 保存绑定关系", LOG_PREFIX, operatorId, id, uid);
        try {
            userService.saveUserRelationRole(uid, id, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 根据角色ID = {} 和用户ID = {} 保存绑定关系失败", LOG_PREFIX, operatorId, id, uid, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 根据角色ID和功能ID保存绑定关系
     *
     * @param id
     * @param fid
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/function/{fid}", method = RequestMethod.GET)
    @ApiOperation(value = "根据角色ID和功能ID保存绑定关系(刘明宇)", notes = "根据角色ID和功能ID保存绑定关系", response = UnifiedResponse.class)
    public UnifiedResponse saveRoleRelationFunction(@PathVariable("id") Long id, @PathVariable("fid") Long fid, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 根据角色ID = {} 和功能ID = {} 保存绑定关系", LOG_PREFIX, operatorId, id, fid);
        try {
            functionService.saveRoleRelationFunction(id, fid, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 根据角色ID = {} 和功能ID = {} 保存绑定关系失败", LOG_PREFIX, operatorId, id, fid, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 更新角色
     *
     * @param role
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "更新角色(刘明宇)", notes = "更新角色", response = UnifiedResponse.class)
    public UnifiedResponse updateRole(@RequestBody Role role, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 更新角色 {}", LOG_PREFIX, operatorId, role);
        try {
            roleService.updateRole(role, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 更新角色 {} 失败", LOG_PREFIX, operatorId, role, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 根据角色ID删除角色
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据角色ID删除角色(刘明宇)", notes = "根据角色ID删除角色", response = UnifiedResponse.class)
    public UnifiedResponse deleteRoleById(@PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 根据角色ID = {} 删除角色", LOG_PREFIX, operatorId, id);
        try {
            roleService.deleteRoleById(id, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 根据角色ID = {} 删除角色失败", LOG_PREFIX, operatorId, id, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 根据角色ID和用户ID删除绑定关系
     *
     * @param id
     * @param uid
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/user/{uid}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据角色ID和用户ID删除绑定关系(刘明宇)", notes = "根据角色ID和用户ID删除绑定关系", response = UnifiedResponse.class)
    public UnifiedResponse deleteUserRelationRole(@PathVariable("id") Long id, @PathVariable("uid") Long uid, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 根据角色ID = {} 和用户ID = {} 删除绑定关系", LOG_PREFIX, operatorId, id, uid);
        try {
            userService.deleteUserRelationRole(uid, id, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 根据角色ID = {} 和用户ID = {} 删除绑定关系失败", LOG_PREFIX, operatorId, id, uid, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 根据角色ID删除所有用户绑定关系
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/users", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据角色ID删除所有用户绑定关系(刘明宇)", notes = "根据角色ID删除所有用户绑定关系", response = UnifiedResponse.class)
    public UnifiedResponse deleteUserRelationRoleByRoleId(@PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 根据角色ID = {} 删除所有用户绑定关系", LOG_PREFIX, operatorId, id);
        try {
            userService.deleteUserRelationRoleByRoleId(id, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 根据角色ID = {} 删除所有用户绑定关系失败", LOG_PREFIX, operatorId, id, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 根据角色ID和功能ID删除绑定关系
     *
     * @param id
     * @param fid
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/function/{fid}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据角色ID和功能ID删除绑定关系(刘明宇)", notes = "根据角色ID和功能ID删除绑定关系", response = UnifiedResponse.class)
    public UnifiedResponse deleteRoleRelationFunction(@PathVariable("id") Long id, @PathVariable("fid") Long fid, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 根据角色ID = {} 和 功能ID = {} 删除绑定关系", LOG_PREFIX, operatorId, id, fid);
        try {
            functionService.deleteRoleRelationFunction(id, fid, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 根据角色ID = {} 和 功能ID = {} 删除绑定关系失败", LOG_PREFIX, operatorId, id, fid);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 根据角色ID删除所有功能绑定关系
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/functions", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据角色ID删除所有功能绑定关系(刘明宇)", notes = "根据角色ID删除所有功能绑定关系", response = UnifiedResponse.class)
    public UnifiedResponse deleteRoleRelationFunctionByRoleId(@PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 根据角色ID = {} 删除所有功能绑定关系", LOG_PREFIX, operatorId, id);
        try {
            functionService.deleteRoleRelationFunctionByRoleId(id, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 根据角色ID = {} 删除所有功能绑定关系失败", LOG_PREFIX, operatorId, id);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 根据角色ID查询角色
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据角色ID查询角色(刘明宇)", notes = "根据角色ID查询角色", response = UnifiedResponse.class)
    public UnifiedResponse queryRoleById(@PathVariable("id") Long id) throws Exception {
        LOGGER.info("{} 根据角色ID = {} 查询角色", LOG_PREFIX, id);
        return new UnifiedResponse(userService.queryUserById(id));
    }

    /**
     * 分页查询角色列表
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
    @ApiOperation(value = "分页查询角色列表(刘明宇)", notes = "分页查询角色列表", response = UnifiedResponse.class)
    public UnifiedResponse pageRoles(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                     @RequestParam(value = "orderField", required = false, defaultValue = "create_time") String orderField,
                                     @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderType) throws Exception {
        LOGGER.info("{} 分页查询角色列表 keyword = {}, page = {}, size = {}", LOG_PREFIX, keyword, page, size);
        return new UnifiedResponse(roleService.pageRoles(keyword, page, size, orderField, orderType));
    }

    /**
     * 根据角色ID分页查询绑定的用户列表
     *
     * @param id
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
    @ApiOperation(value = "根据角色ID分页查询绑定的用户列表(刘明宇)", notes = "根据角色ID分页查询绑定的用户列表", response = UnifiedResponse.class)
    public UnifiedResponse pageUsersByRoleId(@PathVariable("id") Long id,
                                             @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                             @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) throws Exception {
        LOGGER.info("{} 根据角色ID = {} 分页查询绑定的用户列表 page = {}, size = {}", LOG_PREFIX, id, page, size);
        return new UnifiedResponse(userService.pageUsersByRoleId(id, page, size));
    }

    /**
     * 更具角色ID分页查询绑定的功能列表
     *
     * @param id
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/functions", method = RequestMethod.GET)
    @ApiOperation(value = "更具角色ID分页查询绑定的功能列表(刘明宇)", notes = "更具角色ID分页查询绑定的功能列表", response = UnifiedResponse.class)
    public UnifiedResponse pageFunctionsByRoleId(@PathVariable("id") Long id,
                                                 @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) throws Exception {
        LOGGER.info("{} 根据角色ID = {} 分页查询绑定的功能列表 page = {}, size = {}", LOG_PREFIX, id, page, size);
        return new UnifiedResponse(functionService.pageFunctionsByRoleId(id, page, size));
    }
}
