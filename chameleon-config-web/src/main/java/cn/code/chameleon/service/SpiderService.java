package cn.code.chameleon.service;

import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.ChameleonTask;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-05-12 下午1:56
 */
public interface SpiderService {

    String startSpider(Long taskId, Long operatorId) throws ChameleonException;

    String start(Long taskId, Long operatorId) throws ChameleonException;

    String start(ChameleonTask task, Long operatorId) throws ChameleonException;

    void start(List<Long> taskId, Long operatorId) throws ChameleonException;

    void startByGroupId(Long groupId, Long operatorId) throws ChameleonException;

    void stop(Long taskId, Long operatorId) throws ChameleonException;

    void stopAll(Long operatorId) throws ChameleonException;

    void delete(Long taskId, Long operatorId) throws ChameleonException;

    void deleteAll(Long operatorId) throws ChameleonException;

    void createQuartzJob(Long taskId, Long operatorId) throws ChameleonException;

    void removeQuartzJob(Long taskId, Long operatorId) throws ChameleonException;

    void pauseQuartzJob(Long taskId, Long operatorId) throws ChameleonException;

    void resumeQuartzJob(Long taskId, Long operatorId) throws ChameleonException;

    boolean checkQuartzJob(Long taskId) throws ChameleonException;
}
