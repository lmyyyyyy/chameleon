package cn.code.chameleon.service.impl;

import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.mapper.ChameleonTaskMapper;
import cn.code.chameleon.pojo.ChameleonTask;
import cn.code.chameleon.pojo.ChameleonTaskExample;
import cn.code.chameleon.service.ChameleonTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:18
 */
@Service
public class ChameleonTaskServiceImpl implements ChameleonTaskService {

    @Autowired
    private ChameleonTaskMapper chameleonTaskMapper;

    /**
     * 绑定了该模版的任务数量
     *
     * @param templateId
     * @return
     * @throws ChameleonException
     */
    @Override
    public int countTaskByTemplateId(Long templateId) throws ChameleonException {
        ChameleonTaskExample example = new ChameleonTaskExample();
        ChameleonTaskExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        criteria.andTemplateIdEqualTo(templateId);
        return chameleonTaskMapper.countByExample(example);
    }

    /**
     * 绑定了该组的任务数量
     *
     * @param groupId
     * @return
     * @throws ChameleonException
     */
    @Override
    public int countTaskByGroupId(Long groupId) throws ChameleonException {
        ChameleonTaskExample example = new ChameleonTaskExample();
        ChameleonTaskExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        criteria.andGroupIdEqualTo(groupId);
        return chameleonTaskMapper.countByExample(example);
    }
}
