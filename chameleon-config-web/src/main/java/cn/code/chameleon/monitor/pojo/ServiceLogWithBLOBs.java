package cn.code.chameleon.monitor.pojo;

import java.util.Date;

public class ServiceLogWithBLOBs extends ServiceLog {
    private String methodParam;

    private String returnValue;

    private String errorMessage;

    private ServiceLog parent;

    public ServiceLogWithBLOBs(Long id, String className, String methodName, Long timeCost, Long operatorId, String operatorName, Integer invokeStatus, Date addTime, Date updateTime, Long parentId, String methodParam, String returnValue, String errorMessage) {
        super(id, className, methodName, timeCost, operatorId, operatorName, invokeStatus, addTime, updateTime, parentId);
        this.methodParam = methodParam;
        this.returnValue = returnValue;
        this.errorMessage = errorMessage;
    }

    public ServiceLogWithBLOBs() {
        super();
    }

    public String getMethodParam() {
        return methodParam;
    }

    public void setMethodParam(String methodParam) {
        this.methodParam = methodParam == null ? null : methodParam.trim();
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue == null ? null : returnValue.trim();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage == null ? null : errorMessage.trim();
    }

    public ServiceLog getParent() {
        return parent;
    }

    public void setParent(ServiceLog parent) {
        this.parent = parent;
    }
}