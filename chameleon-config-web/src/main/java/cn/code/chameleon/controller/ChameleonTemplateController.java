/*
package cn.code.chameleon.controller;

import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.ChameleonTemplate;
import cn.code.chameleon.service.ChameleonTemplateService;
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
 * @create 2018-05-13 下午8:13
 *//*

@RestController
@RequestMapping("/api/template")
@Api(value = "爬虫模版模块", tags = "爬虫模版模块")
public class ChameleonTemplateController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChameleonTemplateController.class);

    private static final String LOG_PREFIX = "[爬虫模版模块] ";

    @Autowired
    private ChameleonTemplateService chameleonTemplateService;

    */
/**
     * 分页查询模版列表
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
    @ApiOperation(value = "分页查询模版列表(刘明宇)", notes = "分页查询模版列表", response = UnifiedResponse.class)
    public UnifiedResponse pageTemplates(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                         @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                         @RequestParam(value = "orderField", required = false, defaultValue = "update_time") String orderField,
                                         @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderType) throws Exception {
        LOGGER.info("{} 分页查询模版列表 keyword = {}, page = {}, size = {}", LOG_PREFIX, keyword, page, size);
        return new UnifiedResponse(chameleonTemplateService.pageTemplates(keyword, page, size, orderField, orderType));
    }

    */
/**
     * 创建模版
     *
     * @param template
     * @param request
     * @return
     * @throws Exception
     *//*

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "创建模版(刘明宇)", notes = "创建模版", response = UnifiedResponse.class)
    public UnifiedResponse saveTemplate(@RequestBody ChameleonTemplate template, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} {} 创建模版 {}", LOG_PREFIX, operatorId, template);
        try {
            chameleonTemplateService.saveTemplate(template, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} {} 创建模版失败 {}", LOG_PREFIX, operatorId, template, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    */
/**
     * 更新模版
     *
     * @param template
     * @param request
     * @return
     * @throws Exception
     *//*

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "更新模版(刘明宇)", notes = "更新模版", response = UnifiedResponse.class)
    public UnifiedResponse updateTemplate(@RequestBody ChameleonTemplate template, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} {} 更新模版 {}", LOG_PREFIX, operatorId, template);
        try {
            chameleonTemplateService.updateTemplate(template, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} {} 更新模版失败 {}", LOG_PREFIX, operatorId, template, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    */
/**
     * 根据模版ID查询模版信息
     *
     * @param id
     * @return
     * @throws Exception
     *//*

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据模版ID查询模版信息(刘明宇)", notes = "根据模版ID查询模版信息", response = UnifiedResponse.class)
    public UnifiedResponse queryTemplateById(@PathVariable("id") Long id) throws Exception {
        LOGGER.info("{} 根据模版ID = {}查询模版信息", LOG_PREFIX, id);
        return new UnifiedResponse(chameleonTemplateService.queryTemplateById(id));
    }

    */
/**
     * 根据模版ID查询爬虫模版信息
     *
     * @param id
     * @return
     * @throws Exception
     *//*

    @RequestMapping(value = "/spider/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据模版ID查询爬虫模版信息(刘明宇)", notes = "根据模版ID查询爬虫模版信息", response = UnifiedResponse.class)
    public UnifiedResponse querySpiderTemplateById(@PathVariable("id") Long id) throws Exception {
        LOGGER.info("{} 根据模版ID = {}查询爬虫模版信息", LOG_PREFIX, id);
        return new UnifiedResponse(chameleonTemplateService.querySpiderTemplateById(id));
    }
}
*/
