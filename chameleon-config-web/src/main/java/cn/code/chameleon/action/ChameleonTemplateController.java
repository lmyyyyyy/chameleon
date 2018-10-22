package cn.code.chameleon.action;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.ChameleonTemplate;
import cn.code.chameleon.service.ChameleonTemplateService;
import cn.code.chameleon.utils.PageUtil;
import cn.code.chameleon.utils.RequestUtil;
import cn.code.chameleon.vo.ChameleonTemplateVO;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liumingyu
 * @create 2018-05-13 下午8:13
 */
@Controller
@RequestMapping("/api/template")
@Api(value = "爬虫模版模块", tags = "爬虫模版模块")
public class ChameleonTemplateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChameleonTemplateController.class);

    private static final String LOG_PREFIX = "[爬虫模版模块] ";

    @Autowired
    private ChameleonTemplateService chameleonTemplateService;

    @RequestMapping(value = "/to/save", method = RequestMethod.GET)
    public String toSaveTemplate(Model model) throws Exception {
        return "manager/save-template";
    }

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
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String pageTemplates(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                @RequestParam(value = "orderField", required = false, defaultValue = "update_time") String orderField,
                                @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderType,
                                Model model) throws Exception {
        LOGGER.info("{} 分页查询模版列表 keyword = {}, page = {}, size = {}", LOG_PREFIX, keyword, page, size);
        PageData<ChameleonTemplateVO> result = chameleonTemplateService.pageTemplates(keyword, page, size, orderField, orderType);
        model.addAttribute("result", result);
        long totalCount = result.getTotalCount();
        Long pageCount = totalCount % size == 0 ? (totalCount / size) : (totalCount / size) + 1;
        String pageHtml = PageUtil.getPage("/api/template?keyword=" + keyword + "&page={page}&size={size}", page, size, pageCount);
        model.addAttribute("pageHTML", pageHtml);
        return "manager/templateManager";
    }

    /**
     * 创建模版
     *
     * @param template
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveTemplate(ChameleonTemplate template, HttpServletRequest request, Model model) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} {} 创建模版 {}", LOG_PREFIX, operatorId, template);
        try {
            chameleonTemplateService.saveTemplate(template, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} {} 创建模版失败 {}", LOG_PREFIX, operatorId, template, e);
            model.addAttribute("error", e.getMessage());
            return "redirect: /api/function/to/save";
        }
        return "redirect:/api/template";
    }

    /**
     * 更新模版
     *
     * @param template
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String updateTemplate(ChameleonTemplate template, HttpServletRequest request, Model model) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} {} 更新模版 {}", LOG_PREFIX, operatorId, template);
        try {
            chameleonTemplateService.updateTemplate(template, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} {} 更新模版失败 {}", LOG_PREFIX, operatorId, template, e);
            model.addAttribute("error", e.getMessage());
            return "redirect: /api/template/" + template.getId();
        }
        return "redirect:/api/template";
    }

    /**
     * 根据模版ID查询模版信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String queryTemplateById(@PathVariable("id") Long id, Model model) throws Exception {
        LOGGER.info("{} 根据模版ID = {}查询模版信息", LOG_PREFIX, id);
        model.addAttribute("spiderTemplate", chameleonTemplateService.queryTemplateById(id));
        return "manager/templateInfo";
    }

    /**
     * 根据模版ID查询爬虫模版信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/spider/{id}", method = RequestMethod.GET)
    public String querySpiderTemplateById(@PathVariable("id") Long id, Model model) throws Exception {
        LOGGER.info("{} 根据模版ID = {}查询爬虫模版信息", LOG_PREFIX, id);
        model.addAttribute("spiderTemplate", chameleonTemplateService.querySpiderTemplateById(id));
        return "manager/spiderTemplateInfo";
    }

    /**
     * 根据功能ID删除模板
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteTemplate(@PathVariable("id") Long id, HttpServletRequest request, Model model) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 根据模板ID = {} 删除模板", LOG_PREFIX, operatorId, id);
        try {
            chameleonTemplateService.deleteTemplateById(id, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 根据模板ID = {} 删除模板失败", LOG_PREFIX, operatorId, id, e);
            model.addAttribute("error", e.getMessage());
        }
    }
}
