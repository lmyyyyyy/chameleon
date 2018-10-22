package cn.code.chameleon.action;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.Function;
import cn.code.chameleon.service.FunctionService;
import cn.code.chameleon.service.RoleService;
import cn.code.chameleon.utils.PageUtil;
import cn.code.chameleon.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @create 2018-05-07 下午5:34
 */
@Controller
@RequestMapping("/api/function")
@Api(value = "功能模块", tags = "功能模块")
public class FunctionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionController.class);

    private static final String LOG_PREFIX = "[功能模块] ";

    @Autowired
    private RoleService roleService;

    @Autowired
    private FunctionService functionService;

    @RequestMapping(value = "/to/save", method = RequestMethod.GET)
    public String toSaveFunction(Model model) throws Exception {
        return "manager/save-function";
    }

    /**
     * 检查功能标识的有效性 true:有效; false:无效
     *
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    @ApiOperation(value = "检查功能标识的有效性(刘明宇)", notes = "检查功能标识的有效性", response = UnifiedResponse.class)
    public UnifiedResponse checkFunctionCode(@RequestParam(value = "code") String code) throws Exception {
        LOGGER.info("{} 检查功能标识的有效性 code = {}", LOG_PREFIX, code);
        return new UnifiedResponse(functionService.checkFunctionCode(code));
    }

    /**
     * 创建功能
     *
     * @param function
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveFunction(Function function, HttpServletRequest request, Model model) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 创建功能 {}", LOG_PREFIX, operatorId, function);
        try {
            functionService.saveFunction(function, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 创建功能 {} 失败", LOG_PREFIX, operatorId, function);
            model.addAttribute("error", e.getMessage());
            return "redirect: /api/function/to/save";
        }
        return "redirect:/api/function";
    }

    /**
     * 更新功能
     *
     * @param function
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String updateFunction(Function function, HttpServletRequest request, Model model) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 更新功能 {}", LOG_PREFIX, operatorId, function);
        try {
            functionService.updateFunction(function, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 更新功能 {} 失败", LOG_PREFIX, operatorId, function);
            model.addAttribute("error", e.getMessage());
            return "redirect: /api/function/" + function.getId();
        }
        return "redirect:/api/function";
    }

    /**
     * 根据功能ID删除功能
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteFunction(@PathVariable("id") Long id, HttpServletRequest request, Model model) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} operatorId = {} 根据功能ID = {} 删除功能", LOG_PREFIX, operatorId, id);
        try {
            functionService.deleteFunctionById(id, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} operatorId = {} 根据功能ID = {} 删除功能失败", LOG_PREFIX, operatorId, id, e);
            model.addAttribute("error", e.getMessage());
        }
    }

    /**
     * 根据功能ID删除所有角色绑定关系
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/{id}/roles", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据功能ID删除所有角色绑定关系(刘明宇)", notes = "根据功能ID删除所有角色绑定关系", response = UnifiedResponse.class)
    public UnifiedResponse deleteRoleRelationFunctionByFunctionId(@PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} 根据功能ID = {} 删除所有的角色绑定关系", LOG_PREFIX, id);
        try {
            functionService.deleteRoleRelationFunctionByFunctionId(id, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} 根据功能ID = {} 删除所有的角色绑定关系失败", LOG_PREFIX, id);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    /**
     * 根据功能ID查询功能信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String queryFunctionById(@PathVariable("id") Long id, Model model) throws Exception {
        LOGGER.info("{} 根据功能ID = {} 查询功能信息", LOG_PREFIX, id);
        model.addAttribute("function", functionService.queryFunctionById(id));
        return "manager/functionInfo";
    }

    /**
     * 分页查询功能列表
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
    public String pageFunctions(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                @RequestParam(value = "orderField", required = false, defaultValue = "create_time") String orderField,
                                @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderType,
                                Model model) throws Exception {
        LOGGER.info("{} 分页查询功能列表 keyword = {}, page = {}, size = {}", LOG_PREFIX, keyword, page, size);
        PageData<Function> result = functionService.pageFunctions(keyword, page, size, orderField, orderType);
        model.addAttribute("result", result);
        long totalCount = result.getTotalCount();
        Long pageCount = totalCount % size == 0 ? (totalCount / size) : (totalCount / size) + 1;
        String pageHtml = PageUtil.getPage("/api/function?keyword=" + keyword + "&page={page}&size={size}", page, size, pageCount);
        model.addAttribute("pageHTML", pageHtml);
        return "manager/functionManager";
    }

    /**
     * 根据功能ID分页查询角色列表
     *
     * @param id
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/roles", method = RequestMethod.GET)
    @ApiOperation(value = "根据功能ID分页查询角色列表(刘明宇)", notes = "根据功能ID分页查询角色列表", response = UnifiedResponse.class)
    public UnifiedResponse pageRolesByFunctionId(@PathVariable("id") Long id,
                                                 @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) throws Exception {
        LOGGER.info("{} 根据功能ID = {} 分页查询角色列表 page = {}, size = {}", LOG_PREFIX, id, page, size);
        return new UnifiedResponse(functionService.pageRolesByFunctionId(id, page, size));
    }

}
