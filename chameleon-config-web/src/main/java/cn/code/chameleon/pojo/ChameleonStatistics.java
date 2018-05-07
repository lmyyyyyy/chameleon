package cn.code.chameleon.pojo;

import java.util.Date;

public class ChameleonStatistics {
    private Long id;

    private Long taskId;

    private Long crawlCount;

    private Integer stopCount;

    private Long errorCount;

    private Long runHours;

    private Date createTime;

    private Date updateTime;

    private Byte isDelete;

    public ChameleonStatistics(Long id, Long taskId, Long crawlCount, Integer stopCount, Long errorCount, Long runHours, Date createTime, Date updateTime, Byte isDelete) {
        this.id = id;
        this.taskId = taskId;
        this.crawlCount = crawlCount;
        this.stopCount = stopCount;
        this.errorCount = errorCount;
        this.runHours = runHours;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    public ChameleonStatistics() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getCrawlCount() {
        return crawlCount;
    }

    public void setCrawlCount(Long crawlCount) {
        this.crawlCount = crawlCount;
    }

    public Integer getStopCount() {
        return stopCount;
    }

    public void setStopCount(Integer stopCount) {
        this.stopCount = stopCount;
    }

    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    public Long getRunHours() {
        return runHours;
    }

    public void setRunHours(Long runHours) {
        this.runHours = runHours;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}