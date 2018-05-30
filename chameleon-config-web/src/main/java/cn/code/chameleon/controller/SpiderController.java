package cn.code.chameleon.controller;

import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.service.SpiderService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-05-20 下午5:15
 */
@RestController
@RequestMapping("/api/spider")
@Api(value = "爬虫模块", tags = "爬虫模块")
public class SpiderController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderController.class);

    private static final String LOG_PREFIX = "[爬虫模块] ";

    @Autowired
    private SpiderService spiderService;

    /**
     * 启动爬虫
     *
     * @param taskId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "启动爬虫(刘明宇)", notes = "启动爬虫", response = UnifiedResponse.class)
    public UnifiedResponse start(@PathVariable("taskId") Long taskId, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 启动爬虫 taskId = {}", LOG_PREFIX, operatorId, taskId);
        try {
            spiderService.startSpider(taskId, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 启动爬虫失败 taskId = {}", LOG_PREFIX, operatorId, taskId, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 启动爬虫列表
     *
     * @param taskIds
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "启动爬虫列表(刘明宇)", notes = "启动爬虫列表", response = UnifiedResponse.class)
    public UnifiedResponse start(@RequestBody List<Long> taskIds, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 启动爬虫列表 taskIds = {}", LOG_PREFIX, operatorId, taskIds);
        try {
            spiderService.start(taskIds, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 启动爬虫列表失败 taskIds = {}", LOG_PREFIX, operatorId, taskIds, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 启动该组下所有爬虫
     *
     * @param groupId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{groupId}", method = RequestMethod.POST)
    @ApiOperation(value = "启动该组下所有爬虫(刘明宇)", notes = "启动该组下所有爬虫", response = UnifiedResponse.class)
    public UnifiedResponse startByGroupId(@PathVariable("groupId") Long groupId, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 启动该组下所有爬虫 groupId = {}", LOG_PREFIX, operatorId, groupId);
        try {
            spiderService.startByGroupId(groupId, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 启动该组下所有爬虫失败 groupId = {}", LOG_PREFIX, operatorId, groupId, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 关闭爬虫
     *
     * @param taskId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{taskId}", method = RequestMethod.PUT)
    @ApiOperation(value = "关闭爬虫(刘明宇)", notes = "关闭爬虫", response = UnifiedResponse.class)
    public UnifiedResponse stop(@PathVariable("taskId") Long taskId, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 关闭爬虫 taskId = {}", LOG_PREFIX, operatorId, taskId);
        try {
            spiderService.stop(taskId, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 关闭爬虫失败 taskId = {}", LOG_PREFIX, operatorId, taskId, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 关闭所有爬虫
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "关闭所有爬虫(刘明宇)", notes = "关闭所有爬虫", response = UnifiedResponse.class)
    public UnifiedResponse stopAll(HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 关闭所有爬虫", LOG_PREFIX, operatorId);
        try {
            spiderService.stopAll(operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 关闭所有爬虫失败", LOG_PREFIX, operatorId, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 删除爬虫
     *
     * @param taskId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除爬虫(刘明宇)", notes = "删除爬虫", response = UnifiedResponse.class)
    public UnifiedResponse delete(@PathVariable("taskId") Long taskId, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 删除爬虫 taskId = {}", LOG_PREFIX, operatorId, taskId);
        try {
            spiderService.delete(taskId, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 删除爬虫失败 taskId = {}", LOG_PREFIX, operatorId, taskId, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 删除所有爬虫
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除所有爬虫(刘明宇)", notes = "删除所有爬虫", response = UnifiedResponse.class)
    public UnifiedResponse deleteAll(HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 删除所有爬虫", LOG_PREFIX, operatorId);
        try {
            spiderService.deleteAll(operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 删除所有爬虫失败", LOG_PREFIX, operatorId, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 恢复任务
     *
     * @param taskId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/resume/{taskId}", method = RequestMethod.PUT)
    @ApiOperation(value = "恢复任务(刘明宇)", notes = "恢复任务", response = UnifiedResponse.class)
    public UnifiedResponse resumeJob(@PathVariable("taskId") Long taskId, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 恢复任务 taskId = {}", LOG_PREFIX, operatorId, taskId);
        try {
            spiderService.resumeQuartzJob(taskId, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 恢复任务 taskId = {}", LOG_PREFIX, operatorId, taskId);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }
}
