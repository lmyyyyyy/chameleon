package cn.code.chameleon.vo;

import cn.code.chameleon.pojo.ChameleonTemplate;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author liumingyu
 * @create 2018-05-13 下午3:07
 */
@Data
@ToString
public class ChameleonTemplateVO extends ChameleonTemplate implements Serializable {

    private static final long serialVersionUID = 3391806288199579467L;

    /** 引用了该模版的任务数 */
    private Integer taskCount;

    public ChameleonTemplateVO(ChameleonTemplate chameleonTemplate) {
        this.setId(chameleonTemplate.getId());
        this.setDomain(chameleonTemplate.getDomain());
        this.setDescription(chameleonTemplate.getDescription());
        this.setCreateTime(chameleonTemplate.getCreateTime());
        this.setUpdateTime(chameleonTemplate.getUpdateTime());
        this.setOperatorId(chameleonTemplate.getOperatorId());
        this.setIsDelete(chameleonTemplate.getIsDelete());
    }
}
