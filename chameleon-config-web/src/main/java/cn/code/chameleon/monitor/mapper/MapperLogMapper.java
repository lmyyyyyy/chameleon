package cn.code.chameleon.monitor.mapper;

import cn.code.chameleon.monitor.pojo.MapperLog;
import cn.code.chameleon.monitor.pojo.MapperLogExample;
import cn.code.chameleon.monitor.pojo.MapperLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MapperLogMapper {
    int countByExample(MapperLogExample example);

    int deleteByExample(MapperLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MapperLogWithBLOBs record);

    int insertSelective(MapperLogWithBLOBs record);

    List<MapperLogWithBLOBs> selectByExampleWithBLOBs(MapperLogExample example);

    List<MapperLog> selectByExample(MapperLogExample example);

    MapperLogWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MapperLogWithBLOBs record, @Param("example") MapperLogExample example);

    int updateByExampleWithBLOBs(@Param("record") MapperLogWithBLOBs record, @Param("example") MapperLogExample example);

    int updateByExample(@Param("record") MapperLog record, @Param("example") MapperLogExample example);

    int updateByPrimaryKeySelective(MapperLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MapperLogWithBLOBs record);

    int updateByPrimaryKey(MapperLog record);

    int batchInsert(List<MapperLogWithBLOBs> list);
}