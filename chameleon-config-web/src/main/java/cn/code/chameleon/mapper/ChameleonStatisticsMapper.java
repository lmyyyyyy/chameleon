package cn.code.chameleon.mapper;

import cn.code.chameleon.pojo.ChameleonStatistics;
import cn.code.chameleon.pojo.ChameleonStatisticsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChameleonStatisticsMapper {
    int countByExample(ChameleonStatisticsExample example);

    int deleteByExample(ChameleonStatisticsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ChameleonStatistics record);

    int insertSelective(ChameleonStatistics record);

    List<ChameleonStatistics> selectByExample(ChameleonStatisticsExample example);

    ChameleonStatistics selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ChameleonStatistics record, @Param("example") ChameleonStatisticsExample example);

    int updateByExample(@Param("record") ChameleonStatistics record, @Param("example") ChameleonStatisticsExample example);

    int updateByPrimaryKeySelective(ChameleonStatistics record);

    int updateByPrimaryKey(ChameleonStatistics record);
}