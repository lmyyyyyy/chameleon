package cn.code.chameleon.vo;

import cn.code.chameleon.pojo.Role;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author liumingyu
 * @create 2018-05-09 下午3:24
 */
@Data
@ToString
public class RoleVO extends Role implements Serializable {

    private static final long serialVersionUID = -3020629946858441926L;

    private Integer userCount;

    private Integer functionCount;

    public RoleVO(Role role) {
        this.setId(role.getId());
        this.setName(role.getName());
        this.setDescription(role.getDescription());
        this.setCreateTime(role.getCreateTime());
        this.setUpdateTime(role.getUpdateTime());
        this.setIsDelete(role.getIsDelete());
    }

    public RoleVO(Role role, Integer userCount, Integer functionCount) {
        this.setId(role.getId());
        this.setName(role.getName());
        this.setDescription(role.getDescription());
        this.setCreateTime(role.getCreateTime());
        this.setUpdateTime(role.getUpdateTime());
        this.setIsDelete(role.getIsDelete());
        this.setUserCount(userCount);
        this.setFunctionCount(functionCount);
    }

}
