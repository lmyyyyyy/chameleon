package cn.code.chameleon.monitor.pojo;

import java.util.Date;

public class ServiceLog {
    private Long id;

    private String className;

    private String methodName;

    private Long timeCost;

    private Long operatorId;

    private String operatorName;

    private Integer invokeStatus;

    private Date addTime;

    private Date updateTime;

    private Long parentId;

    private int mapperStartIndex;

    private int mapperEndIndex;

    private boolean isTop;

    public ServiceLog(Long id, String className, String methodName, Long timeCost, Long operatorId, String operatorName, Integer invokeStatus, Date addTime, Date updateTime, Long parentId) {
        this.id = id;
        this.className = className;
        this.methodName = methodName;
        this.timeCost = timeCost;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.invokeStatus = invokeStatus;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.parentId = parentId;
    }

    public ServiceLog() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public Long getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Long timeCost) {
        this.timeCost = timeCost;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public Integer getInvokeStatus() {
        return invokeStatus;
    }

    public void setInvokeStatus(Integer invokeStatus) {
        this.invokeStatus = invokeStatus;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getMapperStartIndex() {
        return mapperStartIndex;
    }

    public void setMapperStartIndex(int mapperStartIndex) {
        this.mapperStartIndex = mapperStartIndex;
    }

    public int getMapperEndIndex() {
        return mapperEndIndex;
    }

    public void setMapperEndIndex(int mapperEndIndex) {
        this.mapperEndIndex = mapperEndIndex;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }
}