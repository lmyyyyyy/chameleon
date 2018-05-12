package cn.code.chameleon.service;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.pojo.Group;

/**
 * @author liumingyu
 * @create 2018-05-11 下午8:38
 */
public interface GroupService {

    boolean checkGroupNameUnique(String name) throws ChameleonException;

    void saveGroup(Group group, Long operatorId) throws ChameleonException;

    void updateGroup(Group group, Long operatorId) throws ChameleonException;

    void deleteGroupById(Long id, Long operatorId) throws ChameleonException;

    Group queryGroupById(Long id) throws ChameleonException;

    PageData<Group> pageGroups(String keyword, Integer page, Integer size, String orderField, String orderType) throws ChameleonException;
}
