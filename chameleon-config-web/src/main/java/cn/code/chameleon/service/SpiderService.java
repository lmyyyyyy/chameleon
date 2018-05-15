package cn.code.chameleon.service;

import cn.code.chameleon.exception.ChameleonException;

/**
 * @author liumingyu
 * @create 2018-05-12 下午1:56
 */
public interface SpiderService {

    String start(Long taskId, Long operatorId) throws ChameleonException;
}
