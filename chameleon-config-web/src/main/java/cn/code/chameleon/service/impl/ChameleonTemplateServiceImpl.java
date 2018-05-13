package cn.code.chameleon.service.impl;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.mapper.ChameleonTemplateMapper;
import cn.code.chameleon.model.SpiderTemplate;
import cn.code.chameleon.pojo.ChameleonTemplate;
import cn.code.chameleon.pojo.ChameleonTemplateExample;
import cn.code.chameleon.service.ChameleonTaskService;
import cn.code.chameleon.service.ChameleonTemplateService;
import cn.code.chameleon.spider.enums.DownloaderEnum;
import cn.code.chameleon.spider.enums.DuplicateRemoverEnum;
import cn.code.chameleon.spider.enums.PatternTypeEnum;
import cn.code.chameleon.spider.enums.PipelineEnum;
import cn.code.chameleon.spider.enums.SchedulerEnum;
import cn.code.chameleon.utils.JsonUtils;
import cn.code.chameleon.utils.UrlUtils;
import cn.code.chameleon.vo.ChameleonTemplateVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:18
 */
@Service
public class ChameleonTemplateServiceImpl implements ChameleonTemplateService {

    @Autowired
    private ChameleonTemplateMapper chameleonTemplateMapper;

    @Autowired
    private ChameleonTaskService chameleonTaskService;

    /**
     * 创建爬虫模版
     *
     * @param template
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void saveTemplate(ChameleonTemplate template, Long operatorId) throws ChameleonException {
        if (template == null) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_DATA_EMPTY);
        }
        if (template.getTemplateConfig() == null || "".equals(template.getTemplateConfig())) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_DATA_EMPTY);
        }
        SpiderTemplate spiderTemplate = JsonUtils.jsonToPojo(template.getTemplateConfig(), SpiderTemplate.class);
        if (spiderTemplate == null) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_DATA_EMPTY);
        }
        validteTemplate(spiderTemplate);
        template.setDomain(spiderTemplate.getDomain());
        template.setCreateTime(new Date());
        template.setIsDelete(false);
        template.setUpdateTime(new Date());
        template.setOperatorId(operatorId);
        template.setTemplateConfig(JsonUtils.toJsonStrWithEmptyDefault(spiderTemplate));
        chameleonTemplateMapper.insert(template);
    }

    /**
     * 更新爬虫模版
     *
     * @param template
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void updateTemplate(ChameleonTemplate template, Long operatorId) throws ChameleonException {
        if (template == null) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_DATA_EMPTY);
        }
        if (template.getTemplateConfig() == null || "".equals(template.getTemplateConfig())) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_DATA_EMPTY);
        }
        SpiderTemplate spiderTemplate = JsonUtils.jsonToPojo(template.getTemplateConfig(), SpiderTemplate.class);
        if (spiderTemplate == null) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_DATA_EMPTY);
        }
        validteTemplate(spiderTemplate);
        template.setDomain(spiderTemplate.getDomain());
        template.setUpdateTime(new Date());
        template.setOperatorId(operatorId);
        chameleonTemplateMapper.updateByPrimaryKeySelective(template);
    }

    /**
     * 根据ID删除模版
     *
     * @param id
     * @param operatorId
     * @throws ChameleonException
     */
    @Override
    public void deleteTemplateById(Long id, Long operatorId) throws ChameleonException {
        if (chameleonTaskService.countTaskByTemplateId(id) > 0) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_HAS_BEEN_BIND);
        }
        ChameleonTemplate template = new ChameleonTemplate();
        template.setId(id);
        template.setOperatorId(operatorId);
        template.setIsDelete(true);
        template.setUpdateTime(new Date());
        chameleonTemplateMapper.updateByPrimaryKeySelective(template);
    }

    /**
     * 根据ID查询爬虫模版信息
     *
     * @param id
     * @return
     * @throws ChameleonException
     */
    @Override
    public ChameleonTemplate queryTemplateById(Long id) throws ChameleonException {
        return chameleonTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据ID查询模版信息
     *
     * @param id
     * @return
     * @throws ChameleonException
     */
    @Override
    public SpiderTemplate querySpiderTemplateById(Long id) throws ChameleonException {
        ChameleonTemplate chameleonTemplate = this.queryTemplateById(id);
        if (chameleonTemplate == null) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_DATA_EMPTY);
        }
        if (chameleonTemplate.getTemplateConfig() == null || "".equals(chameleonTemplate.getTemplateConfig())) {
            return new SpiderTemplate();
        }
        return JsonUtils.jsonToPojo(chameleonTemplate.getTemplateConfig(), SpiderTemplate.class);
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
     * @throws ChameleonException
     */
    @Override
    public PageData<ChameleonTemplateVO> pageTemplates(String keyword, Integer page, Integer size, String orderField, String orderType) throws ChameleonException {
        if (page == null || "".equals(page)) {
            page = 1;
        }
        if (size == null || "".equals(size)) {
            size = 20;
        }
        if (orderField == null || "".equals(orderField)) {
            orderField = "update_time";
        }
        if (orderType == null || "".equals(orderType)) {
            orderType = "desc";
        }
        PageHelper.startPage(page, size);
        ChameleonTemplateExample example = new ChameleonTemplateExample();
        ChameleonTemplateExample.Criteria domainCriteria = example.createCriteria();
        ChameleonTemplateExample.Criteria descCriteria = example.createCriteria();
        if (keyword != null && !"".equals(keyword)) {
            domainCriteria.andDomainLike("%" + keyword + "%");
            descCriteria.andDescriptionLike("%" + keyword + "%");
        }
        domainCriteria.andIsDeleteEqualTo(false);
        descCriteria.andIsDeleteEqualTo(false);
        example.or(descCriteria);
        example.setOrderByClause(orderField + " " + orderType);
        List<ChameleonTemplate> templates = chameleonTemplateMapper.selectByExample(example);
        if (templates == null || templates.isEmpty()) {
            return new PageData<>(0, new ArrayList<>());
        }
        PageInfo<ChameleonTemplate> pageInfo = new PageInfo<>(templates);
        List<ChameleonTemplateVO> vos = this.convertTemplates2VOS(templates);
        return new PageData<>(pageInfo.getTotal(), vos);
    }

    /**
     * 验证模版信息
     *
     * @param template
     * @throws ChameleonException
     */
    @Override
    public void validteTemplate(SpiderTemplate template) throws ChameleonException {
        if (template == null) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_DATA_EMPTY);
        }
        if (template.getStartUrls() == null || template.getStartUrls().isEmpty()) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_START_URLS_EMPTY);
        }
        if (template.getTargetUrls() == null || template.getTargetUrls().isEmpty()) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_TARGET_URLS_EMPTY);
        }
        for (SpiderTemplate.TargetUrl targetUrl : template.getTargetUrls()) {
            PatternTypeEnum patternTypeEnum = PatternTypeEnum.getPatternTypeEnum(targetUrl.getTargetUrlType());
            if (patternTypeEnum == null) {
                throw new ChameleonException(ResultCodeEnum.TEMPLATE_PATTERN_TYPE_NOT_EXIST);
            }
            if (targetUrl.getTargetUrlPattern() == null || "".equals(targetUrl.getTargetUrlPattern())) {
                throw new ChameleonException(ResultCodeEnum.TEMPLATE_TARGET_URL_PATTERN_EMPTY);
            }
        }
        if (template.getDomain() == null || "".equals(template.getDomain())) {
            template.setDomain(UrlUtils.getDomain(template.getStartUrls().get(0)));
        }
        DownloaderEnum downloaderEnum = DownloaderEnum.getDownloaderEnum(template.getDownloader());
        if (downloaderEnum == null) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_DOWNLOADER_NOT_EXIST);
        }
        for (int pipelie : template.getPipelines()) {
            PipelineEnum pipelineEnum = PipelineEnum.getPipelineEnum(pipelie);
            if (pipelineEnum == null) {
                throw new ChameleonException(ResultCodeEnum.TEMPLATE_PIPELINE_NOT_EXIST);
            }
        }
        SchedulerEnum schedulerEnum = SchedulerEnum.getSchedulerEnum(template.getScheduler());
        if (schedulerEnum == null) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_SCHEDULER_NOT_EXIST);
        }
        DuplicateRemoverEnum duplicateRemoverEnum = DuplicateRemoverEnum.getDuplicateRemoverEnum(template.getDuplicateRemover());
        if (duplicateRemoverEnum == null) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_DUPLICATE_REMOVER_NOT_EXIST);
        }
        if (template.isCollection()) {
            if (template.getCollectionPattern() == null || "".equals(template.getCollectionPattern())) {
                throw new ChameleonException(ResultCodeEnum.TEMPLATE_COLLECTION_PATTERN_EMPTY);
            }
        }
        if (!template.isSmartCrawler() && (template.getDynamicFields() == null || "".equals(template.getDynamicFields()))) {
            throw new ChameleonException(ResultCodeEnum.TEMPLATE_DYNAMIC_FIELD_EMPTY);
        }
    }

    private List<ChameleonTemplateVO> convertTemplates2VOS(List<ChameleonTemplate> templates) throws ChameleonException {
        if (templates == null || templates.isEmpty()) {
            return new ArrayList<>();
        }
        List<ChameleonTemplateVO> vos = Lists.newArrayListWithCapacity(templates.size());
        for (ChameleonTemplate template : templates) {
            if (template == null) {
                continue;
            }
            ChameleonTemplateVO vo = convertTemplate2VO(template);
            if (vo == null) {
                continue;
            }
            vos.add(vo);
        }
        return vos;
    }

    private ChameleonTemplateVO convertTemplate2VO(ChameleonTemplate template) throws ChameleonException {
        if (template == null) {
            return null;
        }
        ChameleonTemplateVO templateVO = new ChameleonTemplateVO(template);
        templateVO.setTaskCount(chameleonTaskService.countTaskByTemplateId(template.getId()));
        return templateVO;
    }

}
