package cn.code.chameleon.controller;

import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.service.ChameleonStatisticsService;
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
 * @create 2018-05-20 下午5:12
 */
@RestController
@RequestMapping("/api/statistics")
@Api(value = "任务统计模块", tags = "任务统计模块")
public class ChameleonStatisticsController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChameleonStatisticsController.class);

    private static final String LOG_PREFIX = "[任务统计模块] ";

    @Autowired
    private ChameleonStatisticsService chameleonStatisticsService;

    /**
     * 分页查询任务统计
     *
     * @param taskId
     * @param page
     * @param size
     * @param orderField
     * @param orderType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询任务统计(刘明宇)", notes = "分页查询任务统计", response = UnifiedResponse.class)
    public UnifiedResponse pageStatistics(@RequestParam(value = "taskId", required = false, defaultValue = "-1") Long taskId,
                                          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                          @RequestParam(value = "orderField", required = false, defaultValue = "create_time") String orderField,
                                          @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderType) throws Exception {
        LOGGER.info("{} 分页查询任务统计 taskId = {}, page = {}, size = {}", LOG_PREFIX, taskId, page, size);
        return new UnifiedResponse(chameleonStatisticsService.pageStatistics(taskId, page, size, orderField, orderType));
    }

    /**
     * 根据任务统计ID查询统计信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据任务统计ID查询统计信息(刘明宇)", notes = "根据任务统计ID查询统计信息", response = UnifiedResponse.class)
    public UnifiedResponse queryStatisticsById(@PathVariable("id") Long id) throws Exception {
        LOGGER.info("{} 根据统计ID = {}查询任务统计信息", LOG_PREFIX, id);
        return new UnifiedResponse(chameleonStatisticsService.queryStatisticById(id));
    }

}
