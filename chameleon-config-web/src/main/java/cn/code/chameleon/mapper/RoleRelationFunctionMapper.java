package cn.code.chameleon.mapper;

import cn.code.chameleon.pojo.RoleRelationFunction;
import cn.code.chameleon.pojo.RoleRelationFunctionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleRelationFunctionMapper {
    int countByExample(RoleRelationFunctionExample example);

    int deleteByExample(RoleRelationFunctionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleRelationFunction record);

    int insertSelective(RoleRelationFunction record);

    List<RoleRelationFunction> selectByExample(RoleRelationFunctionExample example);

    RoleRelationFunction selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleRelationFunction record, @Param("example") RoleRelationFunctionExample example);

    int updateByExample(@Param("record") RoleRelationFunction record, @Param("example") RoleRelationFunctionExample example);

    int updateByPrimaryKeySelective(RoleRelationFunction record);

    int updateByPrimaryKey(RoleRelationFunction record);
}