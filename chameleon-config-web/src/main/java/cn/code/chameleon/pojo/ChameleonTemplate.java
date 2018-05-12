package cn.code.chameleon.pojo;

import java.util.Date;

public class ChameleonTemplate {
    private Long id;

    private String domain;

    private String description;

    private Long operatorId;

    private Date createTime;

    private Date updateTime;

    private Boolean isDelete;

    private String templateConfig;

    public ChameleonTemplate(Long id, String domain, String description, Long operatorId, Date createTime, Date updateTime, Boolean isDelete, String templateConfig) {
        this.id = id;
        this.domain = domain;
        this.description = description;
        this.operatorId = operatorId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.templateConfig = templateConfig;
    }

    public ChameleonTemplate() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
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

    public String getTemplateConfig() {
        return templateConfig;
    }

    public void setTemplateConfig(String templateConfig) {
        this.templateConfig = templateConfig == null ? null : templateConfig.trim();
    }
}