package cn.code.chameleon.monitor.service;


import cn.code.chameleon.common.PageData;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.monitor.pojo.MapperLogWithBLOBs;
import cn.code.chameleon.monitor.pojo.ServiceLogWithBLOBs;
import cn.code.chameleon.vo.MapperLogVO;
import cn.code.chameleon.vo.ServiceLogVO;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-01-21 下午3:40
 */
public interface RunMethodLogService {

    /**
     * 保存service方法日志
     *
     * @param serviceLogWithBLOBs
     * @return
     * @throws ChameleonException
     */
    int saveServiceLog(ServiceLogWithBLOBs serviceLogWithBLOBs) throws ChameleonException;

    /**
     * 更新service方法日志
     *
     * @param serviceLogWithBLOBs
     * @throws ChameleonException
     */
    void updateServiceLog(ServiceLogWithBLOBs serviceLogWithBLOBs) throws ChameleonException;

    /**
     * 批量保存service方法日志
     *
     * @param serviceLogWithBLOBs
     * @throws ChameleonException
     */
    void batchSaveServiceLog(List<ServiceLogWithBLOBs> serviceLogWithBLOBs);

    /**
     * 保存mapper方法日志
     *
     * @param mapperLogWithBLOBs
     * @return
     * @throws ChameleonException
     */
    int saveMapperLog(MapperLogWithBLOBs mapperLogWithBLOBs) throws ChameleonException;

    /**
     * 更新mapper方法日志
     *
     * @param mapperLogWithBLOBs
     * @throws ChameleonException
     */
    void updateMapperLog(MapperLogWithBLOBs mapperLogWithBLOBs) throws ChameleonException;

    /**
     * 批量保存mapper方法日志
     *
     * @param mapperLogWithBLOBs
     * @throws ChameleonException
     */
    void batchSaveMapperLog(List<MapperLogWithBLOBs> mapperLogWithBLOBs);

    /**
     * 根据id查询service日志
     *
     * @param id
     * @return
     * @throws ChameleonException
     */
    ServiceLogVO queryServiceLogById(Long id) throws ChameleonException;

    /**
     * 根据ID查mapper日志
     *
     * @param id
     * @return
     * @throws ChameleonException
     */
    MapperLogVO queryMapperLogById(Long id) throws ChameleonException;

    /**
     * 分页查询service日志
     *
     * @param serviceLogVO
     * @param page
     * @param size
     * @param orderField
     * @param orderType
     * @return
     * @throws ChameleonException
     */
    PageData<ServiceLogVO> pageServiceLogs(ServiceLogVO serviceLogVO, Integer page, Integer size, String orderField, String orderType) throws ChameleonException;

    /**
     * 分页查询mapper日志
     *
     * @param mapperLogVO
     * @param page
     * @param size
     * @param orderField
     * @param orderType
     * @return
     * @throws ChameleonException
     */
    PageData<MapperLogVO> pageMapperLogs(MapperLogVO mapperLogVO, Integer page, Integer size, String orderField, String orderType) throws ChameleonException;
}



