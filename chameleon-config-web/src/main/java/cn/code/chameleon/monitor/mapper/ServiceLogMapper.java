package cn.code.chameleon.monitor.mapper;

import cn.code.chameleon.monitor.pojo.ServiceLog;
import cn.code.chameleon.monitor.pojo.ServiceLogExample;
import cn.code.chameleon.monitor.pojo.ServiceLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceLogMapper {
    int countByExample(ServiceLogExample example);

    int deleteByExample(ServiceLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ServiceLogWithBLOBs record);

    int insertSelective(ServiceLogWithBLOBs record);

    List<ServiceLogWithBLOBs> selectByExampleWithBLOBs(ServiceLogExample example);

    List<ServiceLog> selectByExample(ServiceLogExample example);

    ServiceLogWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ServiceLogWithBLOBs record, @Param("example") ServiceLogExample example);

    int updateByExampleWithBLOBs(@Param("record") ServiceLogWithBLOBs record, @Param("example") ServiceLogExample example);

    int updateByExample(@Param("record") ServiceLog record, @Param("example") ServiceLogExample example);

    int updateByPrimaryKeySelective(ServiceLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ServiceLogWithBLOBs record);

    int updateByPrimaryKey(ServiceLog record);

    int batchInsert(List<ServiceLogWithBLOBs> list);

    int batchUpdateParentId(@Param("items")List<ServiceLogWithBLOBs> items);
}