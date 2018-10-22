package cn.code.chameleon.action;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.monitor.service.RunMethodLogService;
import cn.code.chameleon.utils.JsonUtils;
import cn.code.chameleon.utils.PageUtil;
import cn.code.chameleon.vo.MapperLogVO;
import cn.code.chameleon.vo.ServiceLogVO;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liumingyu
 * @create 2018-05-21 下午9:08
 */
@Controller
@RequestMapping("/api/monitor")
@Api(value = "监控模块", tags = "监控模块")
public class MonitorController {

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
    public String pageServiceLogs(@RequestParam(value = "issueBody", required = false, defaultValue = "") String issueBody,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                  @RequestParam(value = "orderField", required = false, defaultValue = "add_time") String orderField,
                                  @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderType,
                                  Model model) throws Exception {
        LOGGER.info("{} 分页查询Service日志 page = {}, size = {}", LOG_PREFIX, page, size);
        ServiceLogVO serviceLogVO;
        if (issueBody == null || "".equals(issueBody)) {
            serviceLogVO = new ServiceLogVO();
        } else {
            serviceLogVO = JsonUtils.jsonToPojo(issueBody, ServiceLogVO.class);
        }
        PageData<ServiceLogVO> result = runMethodLogService.pageServiceLogs(serviceLogVO, page, size, orderField, orderType);
        model.addAttribute("result", result);
        long totalCount = result.getTotalCount();
        Long pageCount = totalCount % size == 0 ? (totalCount / size) : (totalCount / size) + 1;
        String pageHtml = PageUtil.getPage("/api/monitor/service?issueBody=" + issueBody + "&page={page}&size={size}", page, size, pageCount);
        model.addAttribute("pageHTML", pageHtml);
        return "manager/serviceManager";
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
    public String pageMapperLogs(@RequestParam(value = "issueBody", required = false, defaultValue = "") String issueBody,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                 @RequestParam(value = "orderField", required = false, defaultValue = "add_time") String orderField,
                                 @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderType,
                                 Model model) throws Exception {
        LOGGER.info("{} 分页查询Mapper日志 page = {}, size = {}", LOG_PREFIX, page, size);
        MapperLogVO mapperLogVO;
        if (issueBody == null || "".equals(issueBody)) {
            mapperLogVO = new MapperLogVO();
        } else {
            mapperLogVO = JsonUtils.jsonToPojo(issueBody, MapperLogVO.class);
        }
        PageData<MapperLogVO> result = runMethodLogService.pageMapperLogs(mapperLogVO, page, size, orderField, orderType);
        model.addAttribute("result", result);
        long totalCount = result.getTotalCount();
        Long pageCount = totalCount % size == 0 ? (totalCount / size) : (totalCount / size) + 1;
        String pageHtml = PageUtil.getPage("/api/monitor/mapper?issueBody=" + issueBody + "&page={page}&size={size}", page, size, pageCount);
        model.addAttribute("pageHTML", pageHtml);
        return "manager/mapperManager";
    }

    /**
     * 根据ID查询Service日志
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/service/{id}", method = RequestMethod.GET)
    public String queryServiceLogById(@PathVariable("id") Long id, Model model) throws Exception {
        LOGGER.info("{} 根据ID = {} 查询Service日志", LOG_PREFIX, id);
        model.addAttribute("service", runMethodLogService.queryServiceLogById(id));

        return "manager/serviceInfo";
    }

    /**
     * 根据ID查询Mapper日志
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mapper/{id}", method = RequestMethod.GET)
    public String queryMapperLogById(@PathVariable("id") Long id, Model model) throws Exception {
        LOGGER.info("{} 根据ID = {} 查询Mapper日志", LOG_PREFIX, id);
        model.addAttribute("mapper", runMethodLogService.queryMapperLogById(id));
        return "manager/mapperInfo";
    }
}
