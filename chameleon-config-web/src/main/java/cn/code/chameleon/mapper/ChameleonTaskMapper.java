package cn.code.chameleon.mapper;

import cn.code.chameleon.pojo.ChameleonTask;
import cn.code.chameleon.pojo.ChameleonTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChameleonTaskMapper {
    int countByExample(ChameleonTaskExample example);

    int deleteByExample(ChameleonTaskExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ChameleonTask record);

    int insertSelective(ChameleonTask record);

    List<ChameleonTask> selectByExample(ChameleonTaskExample example);

    ChameleonTask selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ChameleonTask record, @Param("example") ChameleonTaskExample example);

    int updateByExample(@Param("record") ChameleonTask record, @Param("example") ChameleonTaskExample example);

    int updateByPrimaryKeySelective(ChameleonTask record);

    int updateByPrimaryKey(ChameleonTask record);
}