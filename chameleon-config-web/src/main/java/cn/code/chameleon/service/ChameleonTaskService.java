package cn.code.chameleon.service;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.ChameleonTask;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:16
 */
public interface ChameleonTaskService {

    int countTaskByTemplateId(Long templateId) throws ChameleonException;

    int countTaskByGroupId(Long groupId) throws ChameleonException;

    void saveTask(ChameleonTask task, Long operatorId) throws ChameleonException;

    void udpateTask(ChameleonTask task, Long operatorId) throws ChameleonException;

    void deleteTaskById(Long id, Long operatorId) throws ChameleonException;

    ChameleonTask queryTaskById(Long id) throws ChameleonException;

    PageData<ChameleonTask> pageTasks(Long groupId, Long templateId, Integer status, Integer page, Integer size, String orderField, String orderType) throws ChameleonException;

    void validateTask(ChameleonTask task) throws ChameleonException;
}
