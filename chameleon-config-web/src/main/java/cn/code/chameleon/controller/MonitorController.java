package cn.code.chameleon.controller;

import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.monitor.service.RunMethodLogService;
import cn.code.chameleon.utils.JsonUtils;
import cn.code.chameleon.vo.MapperLogVO;
import cn.code.chameleon.vo.ServiceLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liumingyu
 * @create 2018-05-21 下午9:08
 */
@RestController
@RequestMapping("/api/monitor")
@Api(value = "监控模块", tags = "监控模块")
public class MonitorController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorController.class);

    private static final String LOG_PREFIX = "[监控模块] ";

    @Autowired
    private RunMethodLogService runMethodLogService;

    /**
     * 分页查询Service日志
     *
     * @param issueBody
     * @param page
     * @param size
     * @param orderField
     * @param orderType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询Service日志(刘明宇)", notes = "分页查询Service日志", response = UnifiedResponse.class)
    public UnifiedResponse pageServiceLogs(@RequestParam(value = "issueBody", required = false, defaultValue = "") String issueBody,
                                           @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                           @RequestParam(value = "orderField", required = false, defaultValue = "add_time") String orderField,
                                           @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderType) throws Exception {
        LOGGER.info("{} 分页查询Service日志 page = {}, size = {}", LOG_PREFIX, page, size);
        ServiceLogVO serviceLogVO;
        if (issueBody == null || "".equals(issueBody)) {
            serviceLogVO = new ServiceLogVO();
        } else {
            serviceLogVO = JsonUtils.jsonToPojo(issueBody, ServiceLogVO.class);
        }
        return new UnifiedResponse(runMethodLogService.pageServiceLogs(serviceLogVO, page, size, orderField, orderType));
    }

    /**
     * 分页查询Mapper日志
     *
     * @param issueBody
     * @param page
     * @param size
     * @param orderField
     * @param orderType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mapper", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询Mapper日志(刘明宇)", notes = "分页查询Mapper日志", response = UnifiedResponse.class)
    public UnifiedResponse pageMapperLogs(@RequestParam(value = "issueBody", required = false, defaultValue = "") String issueBody,
                                          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                          @RequestParam(value = "orderField", required = false, defaultValue = "add_time") String orderField,
                                          @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderType) throws Exception {
        LOGGER.info("{} 分页查询Mapper日志 page = {}, size = {}", LOG_PREFIX, page, size);
        MapperLogVO mapperLogVO;
        if (issueBody == null || "".equals(issueBody)) {
            mapperLogVO = new MapperLogVO();
        } else {
            mapperLogVO = JsonUtils.jsonToPojo(issueBody, MapperLogVO.class);
        }
        return new UnifiedResponse(runMethodLogService.pageMapperLogs(mapperLogVO, page, size, orderField, orderType));
    }

    /**
     * 根据ID查询Service日志
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/service/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查询Service日志(刘明宇)", notes = "根据ID查询Service日志", response = UnifiedResponse.class)
    public UnifiedResponse queryServiceLogById(@PathVariable("id") Long id) throws Exception {
        LOGGER.info("{} 根据ID = {} 查询Service日志", LOG_PREFIX, id);
        return new UnifiedResponse(runMethodLogService.queryServiceLogById(id));
    }

    /**
     * 根据ID查询Mapper日志
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mapper/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查询Mapper日志(刘明宇)", notes = "根据ID查询Mapper日志", response = UnifiedResponse.class)
    public UnifiedResponse queryMapperLogById(@PathVariable("id") Long id) throws Exception {
        LOGGER.info("{} 根据ID = {} 查询Mapper日志", LOG_PREFIX, id);
        return new UnifiedResponse(runMethodLogService.queryMapperLogById(id));
    }
}
