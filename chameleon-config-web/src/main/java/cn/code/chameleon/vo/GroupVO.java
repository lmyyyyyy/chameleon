package cn.code.chameleon.vo;

import cn.code.chameleon.pojo.Group;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author liumingyu
 * @create 2018-05-13 下午3:33
 */
@Data
@ToString
public class GroupVO extends Group implements Serializable {

    private static final long serialVersionUID = -6262620144390840274L;

    /**
     * 引用了该模版的任务数
     */
    private Integer taskCount;

    public GroupVO(Group group) {
        this.setId(group.getId());
        this.setName(group.getName());
        this.setDescription(group.getDescription());
        this.setCreateTime(group.getCreateTime());
        this.setIsDelete(group.getIsDelete());
        this.setUpdateTime(group.getUpdateTime());
        this.setOperatorId(group.getOperatorId());
    }
}
