package cn.code.chameleon.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @create 2018-05-09 上午10:48
 */
@Data
@ToString
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 397648126278912321L;

    private Long id;

    private String email;

    private String name;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Boolean isDelete;
}
