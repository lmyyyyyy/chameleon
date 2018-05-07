package cn.code.chameleon.mapper;

import cn.code.chameleon.pojo.UserRelationRole;
import cn.code.chameleon.pojo.UserRelationRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRelationRoleMapper {
    int countByExample(UserRelationRoleExample example);

    int deleteByExample(UserRelationRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserRelationRole record);

    int insertSelective(UserRelationRole record);

    List<UserRelationRole> selectByExample(UserRelationRoleExample example);

    UserRelationRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserRelationRole record, @Param("example") UserRelationRoleExample example);

    int updateByExample(@Param("record") UserRelationRole record, @Param("example") UserRelationRoleExample example);

    int updateByPrimaryKeySelective(UserRelationRole record);

    int updateByPrimaryKey(UserRelationRole record);
}