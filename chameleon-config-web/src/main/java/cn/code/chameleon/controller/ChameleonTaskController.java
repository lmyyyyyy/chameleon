package cn.code.chameleon.controller;

import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.ChameleonTask;
import cn.code.chameleon.service.ChameleonTaskService;
import cn.code.chameleon.utils.RequestUtil;
import io.swagger.annotations.Api;
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

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:35
 */
@RestController
@RequestMapping("/api/task")
@Api(value = "爬虫任务模块", tags = "爬虫任务模块")
public class ChameleonTaskController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChameleonTaskController.class);

    private static final String LOG_PREFIX = "[爬虫任务模块] ";

    @Autowired
    private ChameleonTaskService chameleonTaskService;

    /**
     * 分页查询爬虫列表
     *
     * @param groupId
     * @param templateId
     * @param status
     * @param page
     * @param size
     * @param orderField
     * @param orderTyep
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询爬虫列表(刘明宇)", notes = "分页查询爬虫列表", response = UnifiedResponse.class)
    public UnifiedResponse pageTasks(@RequestParam(value = "groupId", required = false, defaultValue = "-1") Long groupId,
                                     @RequestParam(value = "templateId", required = false, defaultValue = "-1") Long templateId,
                                     @RequestParam(value = "status", required = false, defaultValue = "-1") Integer status,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                     @RequestParam(value = "orderField", required = false, defaultValue = "update_time") String orderField,
                                     @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderTyep) throws Exception {
        LOGGER.info("{} 分页查询爬虫列表 groupId = {}, templateId = {}, status = {}, page = {}, size = {}", LOG_PREFIX, groupId, templateId, size, page, size);
        return new UnifiedResponse(chameleonTaskService.pageSpiders(groupId, templateId, status, page, size, orderField, orderTyep));
    }

    /**
     * 创建爬虫任务
     *
     * @param task
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "创建爬虫任务(刘明宇)", notes = "创建爬虫任务", response = UnifiedResponse.class)
    public UnifiedResponse saveTask(@RequestBody ChameleonTask task, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 创建爬虫任务 task = {}", LOG_PREFIX, operatorId, task);
        try {
            chameleonTaskService.saveTask(task, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 创建爬虫任务失败 task = {}", LOG_PREFIX, operatorId, task, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 更新爬虫任务
     *
     * @param task
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "更新爬虫任务(刘明宇)", notes = "更新爬虫任务", response = UnifiedResponse.class)
    public UnifiedResponse updateTask(@RequestBody ChameleonTask task, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 更新爬虫任务 task = {}", LOG_PREFIX, operatorId, task);
        try {
            chameleonTaskService.updateTask(task, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 更新爬虫任务失败 task = {}", LOG_PREFIX, operatorId, task, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

}
