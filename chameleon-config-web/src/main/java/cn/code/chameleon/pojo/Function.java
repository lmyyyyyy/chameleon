package cn.code.chameleon.pojo;

import java.util.Date;

public class Function {
    private Long id;

    private String code;

    private String description;

    private Date createTime;

    private Date updateTime;

    private Boolean isDelete;

    private String extendField;

    public Function(Long id, String code, String description, Date createTime, Date updateTime, Boolean isDelete, String extendField) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.extendField = extendField;
    }

    public Function() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public String getExtendField() {
        return extendField;
    }

    public void setExtendField(String extendField) {
        this.extendField = extendField == null ? null : extendField.trim();
    }
}