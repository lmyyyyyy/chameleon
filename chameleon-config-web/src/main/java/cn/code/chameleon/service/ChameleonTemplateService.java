package cn.code.chameleon.service;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.model.SpiderTemplate;
import cn.code.chameleon.pojo.ChameleonTemplate;
import cn.code.chameleon.vo.ChameleonTemplateVO;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:16
 */
public interface ChameleonTemplateService {

    void saveTemplate(ChameleonTemplate template, Long operatorId) throws ChameleonException;

    void updateTemplate(ChameleonTemplate template, Long operatorId) throws ChameleonException;

    void deleteTemplateById(Long id, Long operatorId) throws ChameleonException;

    ChameleonTemplate queryTemplateById(Long id) throws ChameleonException;

    SpiderTemplate querySpiderTemplateById(Long id) throws ChameleonException;

    PageData<ChameleonTemplateVO> pageTemplates(String keyword, Integer page, Integer size, String orderField, String orderType) throws ChameleonException;

    void validteTemplate(SpiderTemplate template) throws ChameleonException;
}
