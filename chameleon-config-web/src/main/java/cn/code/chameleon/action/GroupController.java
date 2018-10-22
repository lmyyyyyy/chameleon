package cn.code.chameleon.action;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.Group;
import cn.code.chameleon.service.GroupService;
import cn.code.chameleon.utils.PageUtil;
import cn.code.chameleon.utils.RequestUtil;
import cn.code.chameleon.vo.GroupVO;
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
 * @create 2018-05-11 下午8:58
 */
@Controller
@RequestMapping("/api/group")
@Api(value = "组模块", tags = "组模块")
public class GroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionController.class);

    private static final String LOG_PREFIX = "[组模块] ";

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/to/save", method = RequestMethod.GET)
    public String toSaveGroup(Model model) throws Exception {
        return "manager/save-group";
    }

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
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String pageGroups(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                      @RequestParam(value = "orderField", required = false, defaultValue = "create_time") String orderField,
                                      @RequestParam(value = "orderType", required = false, defaultValue = "desc") String orderType,
                                      Model model) throws Exception {
        LOGGER.info("{} 分页查询组列表 keyword = {}, page = {}, size = {}", LOG_PREFIX, keyword, page, size);
        PageData<GroupVO> result = groupService.pageGroups(keyword, page, size, orderField, orderType);
        model.addAttribute("result", result);
        long totalCount = result.getTotalCount();
        Long pageCount = totalCount % size == 0 ? (totalCount / size) : (totalCount / size) + 1;
        String pageHtml = PageUtil.getPage("/api/group?keyword=" + keyword + "&page={page}&size={size}", page, size, pageCount);
        model.addAttribute("pageHTML", pageHtml);
        return "manager/groupManager";
    }

    /**
     * 创建组
     *
     * @param group
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveGroup(Group group, HttpServletRequest request, Model model) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} {} 创建组 {}", LOG_PREFIX, operatorId, group);
        try {
            groupService.saveGroup(group, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} {} 创建组失败 {}", LOG_PREFIX, operatorId, group, e);
            model.addAttribute("error", e.getMessage());
            return "/api/group/to/save";
        }
        return "redirect:/api/group";
    }

    /**
     * 更新组
     *
     * @param group
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String updateGroup(Group group, HttpServletRequest request, Model model) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} {} 更新组 {}", LOG_PREFIX, operatorId, group);
        try {
            groupService.updateGroup(group, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} {} 更新组失败 {}", LOG_PREFIX, operatorId, group);
            model.addAttribute("error", e.getMessage());
            return "/api/user/" + group.getId();
        }
        return "redirect:/api/group";
    }

    /**
     * 根据ID删除组
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteGroupById(@PathVariable("id") Long id, HttpServletRequest request, Model model) throws Exception {
        Long operatorId = RequestUtil.getCurrentUserId();
        LOGGER.info("{} {} 根据id = {} 删除组", LOG_PREFIX, operatorId, id);
        try {
            groupService.deleteGroupById(id, operatorId);
        } catch (ChameleonException e) {
            LOGGER.error("{} {} 根据id = {} 删除组失败", LOG_PREFIX, operatorId, id);
            model.addAttribute("error", e.getMessage());
        }
    }

    /**
     * 根据ID查询组信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查询组信息(刘明宇)", notes = "根据ID查询组信息", response = UnifiedResponse.class)
    public String queryGroupById(@PathVariable("id") Long id, Model model) throws Exception {
        LOGGER.info("{} 根据ID = {} 查询组信息", LOG_PREFIX, id);
        model.addAttribute("group", groupService.queryGroupById(id));
        return "manager/groupInfo";
    }
}
