package cn.code.chameleon.pojo;

import java.util.Date;

public class RoleRelationFunction {
    private Long id;

    private Long roleId;

    private Long functionId;

    private Date createTime;

    private Date updateTime;

    private Long operatorId;

    private Byte isDelete;

    public RoleRelationFunction(Long id, Long roleId, Long functionId, Date createTime, Date updateTime, Long operatorId, Byte isDelete) {
        this.id = id;
        this.roleId = roleId;
        this.functionId = functionId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.operatorId = operatorId;
        this.isDelete = isDelete;
    }

    public RoleRelationFunction() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
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

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}