package cn.code.chameleon.vo;

import cn.code.chameleon.pojo.User;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-05-09 下午3:33
 */
@Data
@ToString
public class UserVO extends User implements Serializable {

    private static final long serialVersionUID = -8205670158697601945L;

    private List<String> codes;

    public UserVO() {
    }

    public UserVO(User user) {
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setName(user.getName());
        this.setStatus(user.getStatus());
        this.setCreateTime(user.getCreateTime());
        this.setUpdateTime(user.getUpdateTime());
        this.setIsDelete(user.getIsDelete());
    }

}
