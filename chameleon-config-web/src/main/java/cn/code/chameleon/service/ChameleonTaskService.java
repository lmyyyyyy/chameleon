package cn.code.chameleon.service;

import cn.code.chameleon.exception.ChameleonException;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:16
 */
public interface ChameleonTaskService {

    int countTaskByTemplateId(Long templateId) throws ChameleonException;
}
