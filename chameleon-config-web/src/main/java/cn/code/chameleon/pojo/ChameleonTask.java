package cn.code.chameleon.pojo;

import java.util.Date;

public class ChameleonTask {
    private Long id;

    private Long templateId;

    private Long groupId;

    private String jobName;

    private String jobGroup;

    private String jobClassName;

    private String triggerName;

    private String triggerGroup;

    private String expression;

    private Integer status;

    private Boolean needSync;

    private Long repeatInterval;

    private Long repeatCount;

    private String timeZoneId;

    private String description;

    private Long operatorId;

    private Date createTime;

    private Date updateTime;

    private Boolean isDelete;

    public ChameleonTask(Long id, Long templateId, Long groupId, String jobName, String jobGroup, String jobClassName, String triggerName, String triggerGroup, String expression, Integer status, Boolean needSync, Long repeatInterval, Long repeatCount, String timeZoneId, String description, Long operatorId, Date createTime, Date updateTime, Boolean isDelete) {
        this.id = id;
        this.templateId = templateId;
        this.groupId = groupId;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobClassName = jobClassName;
        this.triggerName = triggerName;
        this.triggerGroup = triggerGroup;
        this.expression = expression;
        this.status = status;
        this.needSync = needSync;
        this.repeatInterval = repeatInterval;
        this.repeatCount = repeatCount;
        this.timeZoneId = timeZoneId;
        this.description = description;
        this.operatorId = operatorId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    public ChameleonTask() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup == null ? null : jobGroup.trim();
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName == null ? null : jobClassName.trim();
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName == null ? null : triggerName.trim();
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup == null ? null : triggerGroup.trim();
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression == null ? null : expression.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getNeedSync() {
        return needSync;
    }

    public void setNeedSync(Boolean needSync) {
        this.needSync = needSync;
    }

    public Long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(Long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public Long getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Long repeatCount) {
        this.repeatCount = repeatCount;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId == null ? null : timeZoneId.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}