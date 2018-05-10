package cn.code.chameleon.monitor.service.impl;

import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.monitor.mapper.MapperLogMapper;
import cn.code.chameleon.monitor.mapper.ServiceLogMapper;
import cn.code.chameleon.monitor.pojo.MapperLogWithBLOBs;
import cn.code.chameleon.monitor.pojo.ServiceLogWithBLOBs;
import cn.code.chameleon.monitor.service.RunMethodLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-01-21 下午3:41
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Throwable.class)
public class RunMethodLogServiceImpl implements RunMethodLogService {

    @Autowired
    private ServiceLogMapper runServiceLogMapper;

    @Autowired
    private MapperLogMapper runMapperLogMapper;

    /**
     * 保存serivce日志
     *
     * @param runServiceLogWithBLOBs
     * @return
     * @throws ChameleonException
     */
    @Override
    public int saveServiceLog(ServiceLogWithBLOBs runServiceLogWithBLOBs) throws ChameleonException {
        if (runServiceLogWithBLOBs == null) {
            return -1;
        }
        return runServiceLogMapper.insert(runServiceLogWithBLOBs);
    }

    /**
     * 更新service日志
     *
     * @param runServiceLogWithBLOBs
     * @throws ChameleonException
     */
    @Override
    public void updateServiceLog(ServiceLogWithBLOBs runServiceLogWithBLOBs) throws ChameleonException {
        if (runServiceLogWithBLOBs == null) {
            return;
        }
        if (runServiceLogWithBLOBs.getId() == null || runServiceLogWithBLOBs.getId() == 0) {
            return;
        }
        runServiceLogMapper.updateByPrimaryKeySelective(runServiceLogWithBLOBs);
    }

    /**
     * 批量保存service日志
     *
     * @param runServiceLogWithBLOBsList
     * @throws ChameleonException
     */
    @Override
    public void batchSaveServiceLog(List<ServiceLogWithBLOBs> runServiceLogWithBLOBsList) {
        if (runServiceLogWithBLOBsList == null || runServiceLogWithBLOBsList.size() == 0) {
            return;
        }
        runServiceLogMapper.batchInsert(runServiceLogWithBLOBsList);
        if (runServiceLogWithBLOBsList.size() > 1) {
            runServiceLogMapper.batchUpdateParentId(runServiceLogWithBLOBsList);
        }
    }

    /**
     * 保存mapper日志
     *
     * @param runMapperLogWithBLOBs
     * @return
     * @throws ChameleonException
     */
    @Override
    public int saveMapperLog(MapperLogWithBLOBs runMapperLogWithBLOBs) throws ChameleonException {
        if (runMapperLogWithBLOBs == null) {
            return -1;
        }
        return runMapperLogMapper.insert(runMapperLogWithBLOBs);
    }

    /**
     * 更新mapper日志
     *
     * @param runMapperLogWithBLOBs
     * @throws ChameleonException
     */
    @Override
    public void updateMapperLog(MapperLogWithBLOBs runMapperLogWithBLOBs) throws ChameleonException {
        if (runMapperLogWithBLOBs == null) {

        }
        runMapperLogMapper.updateByPrimaryKeySelective(runMapperLogWithBLOBs);
    }

    /**
     * 批量保存mapper日志
     *
     * @param runMapperLogWithBLOBs
     * @throws ChameleonException
     */
    @Override
    public void batchSaveMapperLog(List<MapperLogWithBLOBs> runMapperLogWithBLOBs) {
        if (runMapperLogWithBLOBs == null || runMapperLogWithBLOBs.size() == 0) {
            return;
        }
        runMapperLogMapper.batchInsert(runMapperLogWithBLOBs);
    }

    /**
     * 根据id查询service日志
     *
     * @param id
     * @return
     * @throws ChameleonException
     */
    @Override
    public ServiceLogWithBLOBs queryServiceLogById(Long id) throws ChameleonException {
        if (id == null || id <= 0) {
            throw new ChameleonException(ResultCodeEnum.INPUT_ERROR);
        }
        return runServiceLogMapper.selectByPrimaryKey(id);
    }


}


