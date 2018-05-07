package cn.code.chameleon.mapper;

import cn.code.chameleon.pojo.ChameleonTemplate;
import cn.code.chameleon.pojo.ChameleonTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChameleonTemplateMapper {
    int countByExample(ChameleonTemplateExample example);

    int deleteByExample(ChameleonTemplateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ChameleonTemplate record);

    int insertSelective(ChameleonTemplate record);

    List<ChameleonTemplate> selectByExampleWithBLOBs(ChameleonTemplateExample example);

    List<ChameleonTemplate> selectByExample(ChameleonTemplateExample example);

    ChameleonTemplate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ChameleonTemplate record, @Param("example") ChameleonTemplateExample example);

    int updateByExampleWithBLOBs(@Param("record") ChameleonTemplate record, @Param("example") ChameleonTemplateExample example);

    int updateByExample(@Param("record") ChameleonTemplate record, @Param("example") ChameleonTemplateExample example);

    int updateByPrimaryKeySelective(ChameleonTemplate record);

    int updateByPrimaryKeyWithBLOBs(ChameleonTemplate record);

    int updateByPrimaryKey(ChameleonTemplate record);
}