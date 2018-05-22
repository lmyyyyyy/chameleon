package cn.code.chameleon.monitor.service.impl;

import cn.code.chameleon.common.PageData;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.monitor.mapper.MapperLogMapper;
import cn.code.chameleon.monitor.mapper.ServiceLogMapper;
import cn.code.chameleon.monitor.pojo.MapperLogExample;
import cn.code.chameleon.monitor.pojo.MapperLogWithBLOBs;
import cn.code.chameleon.monitor.pojo.ServiceLogExample;
import cn.code.chameleon.monitor.pojo.ServiceLogWithBLOBs;
import cn.code.chameleon.monitor.service.RunMethodLogService;
import cn.code.chameleon.vo.MapperLogVO;
import cn.code.chameleon.vo.ServiceLogVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-01-21 下午3:41
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Throwable.class)
public class RunMethodLogServiceImpl implements RunMethodLogService {

    @Autowired
    private ServiceLogMapper serviceLogMapper;

    @Autowired
    private MapperLogMapper mapperLogMapper;

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
        return serviceLogMapper.insert(runServiceLogWithBLOBs);
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
        serviceLogMapper.updateByPrimaryKeySelective(runServiceLogWithBLOBs);
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
        serviceLogMapper.batchInsert(runServiceLogWithBLOBsList);
        if (runServiceLogWithBLOBsList.size() > 1) {
            serviceLogMapper.batchUpdateParentId(runServiceLogWithBLOBsList);
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
        return mapperLogMapper.insert(runMapperLogWithBLOBs);
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
        mapperLogMapper.updateByPrimaryKeySelective(runMapperLogWithBLOBs);
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
        mapperLogMapper.batchInsert(runMapperLogWithBLOBs);
    }

    /**
     * 根据id查询service日志
     *
     * @param id
     * @return
     * @throws ChameleonException
     */
    @Override
    public ServiceLogVO queryServiceLogById(Long id) throws ChameleonException {
        if (id == null || id <= 0) {
            throw new ChameleonException(ResultCodeEnum.INPUT_ERROR);
        }
        return convertServiceLog2VO(serviceLogMapper.selectByPrimaryKey(id));
    }

    @Override
    public MapperLogVO queryMapperLogById(Long id) throws ChameleonException {
        if (id == null || id <= 0) {
            throw new ChameleonException(ResultCodeEnum.INPUT_ERROR);
        }
        return convertMapperLog2VO(mapperLogMapper.selectByPrimaryKey(id));
    }

    @Override
    public PageData<ServiceLogVO> pageServiceLogs(ServiceLogVO serviceLogVO, Integer page, Integer size, String orderField, String orderType) throws ChameleonException {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 20;
        }
        if (orderField == null || "".equals(orderField)) {
            orderField = "add_time";
        }
        if (orderType == null || "".equals(orderType)) {
            orderType = "desc";
        }
        PageHelper.startPage(page, size);
        ServiceLogExample example = new ServiceLogExample();
        ServiceLogExample.Criteria criteria = example.createCriteria();
        if (serviceLogVO != null) {
            if (serviceLogVO.getClassName() != null && !"".equals(serviceLogVO.getClassName())) {
                criteria.andClassNameLike("%" + serviceLogVO.getClassName() + "%");
            } else if (serviceLogVO.getMethodName() != null && !"".equals(serviceLogVO.getMethodName())) {
                criteria.andMethodNameLike("%" + serviceLogVO.getMethodName() + "%");
            } else if (serviceLogVO.getMethodParam() != null && !"".equals(serviceLogVO.getMethodParam())) {
                criteria.andMethodParamLike("%" + serviceLogVO.getMethodParam() + "%");
            } else if (serviceLogVO.getErrorMessage() != null && !"".equals(serviceLogVO.getErrorMessage())) {
                criteria.andErrorMessageLike("%" + serviceLogVO.getErrorMessage() + "%");
            } else if (serviceLogVO.getReturnValue() != null && !"".equals(serviceLogVO.getReturnValue())) {
                criteria.andReturnValueLike("%" + serviceLogVO.getReturnValue() + "%");
            } else if (serviceLogVO.getOperatorName() != null && !"".equals(serviceLogVO.getOperatorName())) {
                criteria.andOperatorNameLike("%" + serviceLogVO.getOperatorName() + "%");
            }
            if (serviceLogVO.getInvokeStatus() != null && serviceLogVO.getInvokeStatus() >= 0) {
                criteria.andInvokeStatusEqualTo(serviceLogVO.getInvokeStatus());
            }
            if (serviceLogVO.getParentId() != null && serviceLogVO.getParentId() >= 0) {
                criteria.andParentIdEqualTo(serviceLogVO.getParentId());
            }
            if (serviceLogVO.getBeginTime() != null) {
                criteria.andAddTimeGreaterThanOrEqualTo(serviceLogVO.getBeginTime());
            }
            if (serviceLogVO.getEndTime() != null) {
                criteria.andAddTimeLessThanOrEqualTo(serviceLogVO.getEndTime());
            }
            if (serviceLogVO.getBeginCost() != null) {
                criteria.andTimeCostGreaterThanOrEqualTo(serviceLogVO.getBeginCost());
            }
            if (serviceLogVO.getEndCost() != null) {
                criteria.andTimeCostLessThanOrEqualTo(serviceLogVO.getEndCost());
            }
        }
        example.setOrderByClause(orderField + " " + orderType);
        List<ServiceLogWithBLOBs> serviceLogWithBLOBs = serviceLogMapper.selectByExampleWithBLOBs(example);
        PageInfo<ServiceLogWithBLOBs> pageInfo = new PageInfo<>(serviceLogWithBLOBs);
        List<ServiceLogVO> serviceLogVOS = convertServiceLogs2VOS(serviceLogWithBLOBs);
        return new PageData<>(pageInfo.getTotal(), serviceLogVOS);
    }

    @Override
    public PageData<MapperLogVO> pageMapperLogs(MapperLogVO mapperLogVO, Integer page, Integer size, String orderField, String orderType) throws ChameleonException {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 20;
        }
        if (orderField == null || "".equals(orderField)) {
            orderField = "add_time";
        }
        if (orderType == null || "".equals(orderType)) {
            orderType = "desc";
        }
        PageHelper.startPage(page, size);
        MapperLogExample example = new MapperLogExample();
        MapperLogExample.Criteria criteria = example.createCriteria();
        if (mapperLogVO != null) {
            if (mapperLogVO.getMethodName() != null && !"".equals(mapperLogVO.getMethodName())) {
                criteria.andMethodNameLike("%" + mapperLogVO.getMethodName() + "%");
            } else if (mapperLogVO.getMethodParam() != null && !"".equals(mapperLogVO.getMethodParam())) {
                criteria.andMethodParamLike("%" + mapperLogVO.getMethodParam() + "%");
            } else if (mapperLogVO.getErrorMessage() != null && !"".equals(mapperLogVO.getErrorMessage())) {
                criteria.andErrorMessageLike("%" + mapperLogVO.getErrorMessage() + "%");
            } else if (mapperLogVO.getSqlStatement() != null && !"".equals(mapperLogVO.getSqlStatement())) {
                criteria.andSqlStatementLike("%" + mapperLogVO.getSqlStatement() + "%");
            }
            if (mapperLogVO.getTargetTableName() != null && !"".equals(mapperLogVO.getTargetTableName())) {
                criteria.andTargetTableNameEqualTo(mapperLogVO.getTargetTableName());
            }
            if (mapperLogVO.getInvokeStatus() != null && mapperLogVO.getInvokeStatus() >= 0) {
                criteria.andInvokeStatusEqualTo(mapperLogVO.getInvokeStatus());
            }
            if (mapperLogVO.getSerivceLogId() != null && mapperLogVO.getSerivceLogId() >= 0) {
                criteria.andSerivceLogIdEqualTo(mapperLogVO.getSerivceLogId());
            }
            if (mapperLogVO.getBeginTime() != null) {
                criteria.andAddTimeGreaterThanOrEqualTo(mapperLogVO.getBeginTime());
            }
            if (mapperLogVO.getEndTime() != null) {
                criteria.andAddTimeLessThanOrEqualTo(mapperLogVO.getEndTime());
            }
            if (mapperLogVO.getBeginCost() != null) {
                criteria.andTimeCostGreaterThanOrEqualTo(mapperLogVO.getBeginCost());
            }
            if (mapperLogVO.getEndCost() != null) {
                criteria.andTimeCostLessThanOrEqualTo(mapperLogVO.getEndCost());
            }
            if (mapperLogVO.getOperateType() != null) {
                criteria.andOperateTypeEqualTo(mapperLogVO.getOperateType());
            }
        }
        example.setOrderByClause(orderField + " " + orderType);
        List<MapperLogWithBLOBs> mapperLogWithBLOBs = mapperLogMapper.selectByExampleWithBLOBs(example);
        PageInfo<MapperLogWithBLOBs> pageInfo = new PageInfo<>(mapperLogWithBLOBs);
        List<MapperLogVO> mapperLogVOS = convertMapperLogs2VOS(mapperLogWithBLOBs);
        return new PageData<>(pageInfo.getTotal(), mapperLogVOS);
    }

    private List<ServiceLogVO> convertServiceLogs2VOS(List<ServiceLogWithBLOBs> serviceLogWithBLOBs) {
        if (serviceLogWithBLOBs == null || serviceLogWithBLOBs.isEmpty()) {
            return new ArrayList<>();
        }
        List<ServiceLogVO> serviceLogVOS = Lists.newArrayListWithCapacity(serviceLogWithBLOBs.size());
        for (ServiceLogWithBLOBs serviceLogWithBLOBs1 : serviceLogWithBLOBs) {
            if (serviceLogWithBLOBs1 == null) {
                continue;
            }
            ServiceLogVO serviceLogVO = convertServiceLog2VO(serviceLogWithBLOBs1);
            if (serviceLogVO == null) {
                continue;
            }
            serviceLogVOS.add(serviceLogVO);
        }
        return serviceLogVOS;
    }

    private ServiceLogVO convertServiceLog2VO(ServiceLogWithBLOBs serviceLogWithBLOBs) {
        if (serviceLogWithBLOBs == null) {
            return null;
        }
        ServiceLogVO serviceLogVO = new ServiceLogVO(serviceLogWithBLOBs);
        return serviceLogVO;
    }

    private List<MapperLogVO> convertMapperLogs2VOS(List<MapperLogWithBLOBs> mapperLogWithBLOBs) {
        if (mapperLogWithBLOBs == null || mapperLogWithBLOBs.isEmpty()) {
            return new ArrayList<>();
        }
        List<MapperLogVO> mapperLogVOS = Lists.newArrayListWithCapacity(mapperLogWithBLOBs.size());
        for (MapperLogWithBLOBs mapperLogWithBLOBs1 : mapperLogWithBLOBs) {
            if (mapperLogWithBLOBs1 == null) {
                continue;
            }
            MapperLogVO mapperLogVO = convertMapperLog2VO(mapperLogWithBLOBs1);
            if (mapperLogVO == null) {
                continue;
            }
            mapperLogVOS.add(mapperLogVO);
        }
        return mapperLogVOS;
    }

    private MapperLogVO convertMapperLog2VO(MapperLogWithBLOBs mapperLogWithBLOBs) {
        if (mapperLogWithBLOBs == null) {
            return null;
        }
        MapperLogVO mapperLogVO = new MapperLogVO(mapperLogWithBLOBs);
        return mapperLogVO;
    }


}


