package cn.code.chameleon.vo;

import cn.code.chameleon.monitor.pojo.ServiceLogWithBLOBs;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @create 2018-05-21 下午6:48
 */
@Data
@ToString
public class ServiceLogVO implements Serializable {

    private static final long serialVersionUID = 1470155036936950999L;

    /**
     * ID
     */
    private Long id;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法参数
     */
    private String methodParam;

    /**
     * 返回值
     */
    private String returnValue;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 方法执行用时
     */
    private Long timeCost;

    /**
     * 操作人
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 执行状态
     */
    private Integer invokeStatus;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 父ID
     */
    private Long parentId;


    private Date beginTime;

    private Date endTime;

    private Long beginCost;

    private Long endCost;

    public ServiceLogVO() {}

    public ServiceLogVO(ServiceLogWithBLOBs serviceLogWithBLOBs) {
        this.id = serviceLogWithBLOBs.getId();
        this.className = serviceLogWithBLOBs.getClassName();
        this.methodName = serviceLogWithBLOBs.getMethodName();
        this.methodParam = serviceLogWithBLOBs.getMethodParam();
        this.returnValue = serviceLogWithBLOBs.getReturnValue();
        this.errorMessage = serviceLogWithBLOBs.getErrorMessage();
        this.timeCost = serviceLogWithBLOBs.getTimeCost();
        this.operatorId = serviceLogWithBLOBs.getOperatorId();
        this.operatorName = serviceLogWithBLOBs.getOperatorName();
        this.invokeStatus = serviceLogWithBLOBs.getInvokeStatus();
        this.addTime = serviceLogWithBLOBs.getAddTime();
        this.updateTime = serviceLogWithBLOBs.getUpdateTime();
        this.parentId = serviceLogWithBLOBs.getParentId();
    }
}
