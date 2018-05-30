package cn.code.chameleon.vo;

import cn.code.chameleon.monitor.pojo.MapperLogWithBLOBs;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @create 2018-05-21 下午6:46
 */
@Data
@ToString
public class MapperLogVO implements Serializable {

    private static final long serialVersionUID = 3577090430198800863L;

    /**
     * ID
     */
    private Long id;

    /**
     * Service方法ID
     */
    private Long serivceLogId;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 方法参数
     */
    private String methodParam;

    /**
     * sql语句
     */
    private String sqlStatement;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 目标表名
     */
    private String targetTableName;

    /**
     * 操作类型
     */
    private Byte operateType;

    /**
     * 影响行数
     */
    private Integer influenceRow;

    /**
     * 执行状态
     */
    private Byte invokeStatus;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 方法执行用时
     */
    private Long timeCost;

    private Date beginTime;

    private Date endTime;

    private Long beginCost;

    private Long endCost;

    public MapperLogVO() {}

    public MapperLogVO(MapperLogWithBLOBs mapperLogWithBLOBs) {
        this.id = mapperLogWithBLOBs.getId();
        this.serivceLogId = mapperLogWithBLOBs.getSerivceLogId();
        this.methodName = mapperLogWithBLOBs.getMethodName();
        this.methodParam = mapperLogWithBLOBs.getMethodParam();
        this.sqlStatement = mapperLogWithBLOBs.getSqlStatement();
        this.errorMessage = mapperLogWithBLOBs.getErrorMessage();
        this.targetTableName = mapperLogWithBLOBs.getTargetTableName();
        this.operateType = mapperLogWithBLOBs.getOperateType();
        this.influenceRow = mapperLogWithBLOBs.getInfluenceRow();
        this.invokeStatus = mapperLogWithBLOBs.getInvokeStatus();
        this.addTime = mapperLogWithBLOBs.getAddTime();
        this.updateTime = mapperLogWithBLOBs.getUpdateTime();
        this.timeCost = mapperLogWithBLOBs.getTimeCost();
    }
}
