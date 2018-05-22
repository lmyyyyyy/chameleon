package cn.code.chameleon.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @create 2018-05-16 下午5:04
 */
@Data
@ToString
public class SpiderVO implements Serializable {

    private static final long serialVersionUID = -6845223386543603286L;

    private String uuid;

    private String domain;

    private Long groupId;

    private Long templateId;

    private Long taskId;

    private Long pageCount;

    private Date startTime;

    private Integer treadAlive;

    private Integer status;
}
