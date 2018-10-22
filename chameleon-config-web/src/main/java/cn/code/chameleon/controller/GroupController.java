/*
package cn.code.chameleon.controller;

import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.Group;
import cn.code.chameleon.service.GroupService;
import cn.code.chameleon.utils.RequestUtil;
import io.swagger.annotations.Api;
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

*/
/**
 * @author liumingyu
 * @create 2018-05-11 下午8:58
 *//*

@RestController
@RequestMapping("/api/group")
@Api(value = "组模块", tags = "组模块")
public class GroupController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionController.class);

    private static final String LOG_PREFIX = "[组模块] ";

    @Autowired
    private GroupService groupService;

    */
/**
     * 分页查询组列表
     *
     * @param keyword
     * @param page
     * @param size
     * @param orderField
     * @param orderType
     * @return
     * @throws Exception
     *//*

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询组列表(刘明宇)", notes = "分页查询组列表", response = UnifiedResponse.class)
    public UnifiedResponse pageGroups(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                      @RequestParam(value = "orderField", required = false, defaultValue = "create_time") String orderField,
                                      @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderType) throws Exception {
        LOGGER.info("{} 分页查询组列表 keyword = {}, page = {}, size = {}", LOG_PREFIX, keyword, page, size);
        return new UnifiedResponse(groupService.pageGroups(keyword, page, size, orderField, orderType));
    }

    */
/**
     * 创建组
     *
     * @param group
     * @param request
     * @return
     * @throws Exception
     *//*

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "创建组(刘明宇)", notes = "创建组", response = UnifiedResponse.class)
    public UnifiedResponse saveGroup(@RequestBody Group group, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} {} 创建组 {}", LOG_PREFIX, operatorId, group);
        try {
            groupService.saveGroup(group, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} {} 创建组失败 {}", LOG_PREFIX, operatorId, group, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    */
/**
     * 更新组
     *
     * @param group
     * @param request
     * @return
     * @throws Exception
     *//*

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "更新组(刘明宇)", notes = "更新组", response = UnifiedResponse.class)
    public UnifiedResponse updateGroup(@RequestBody Group group, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} {} 更新组 {}", LOG_PREFIX, operatorId, group);
        try {
            groupService.updateGroup(group, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} {} 更新组失败 {}", LOG_PREFIX, operatorId, group);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    */
/**
     * 根据ID删除组
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     *//*

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除组(刘明宇)", notes = "根据ID删除组", response = UnifiedResponse.class)
    public UnifiedResponse deleteGroupById(@PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} {} 根据id = {} 删除组", LOG_PREFIX, operatorId, id);
        try {
            groupService.deleteGroupById(id, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} {} 根据id = {} 删除组失败", LOG_PREFIX, operatorId, id);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    */
/**
     * 根据ID查询组信息
     *
     * @param id
     * @return
     * @throws Exception
     *//*

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查询组信息(刘明宇)", notes = "根据ID查询组信息", response = UnifiedResponse.class)
    public UnifiedResponse queryGroupById(@PathVariable("id") Long id) throws Exception {
        LOGGER.info("{} 根据ID = {} 查询组信息", LOG_PREFIX, id);
        return new UnifiedResponse(groupService.queryGroupById(id));
    }
}
*/
