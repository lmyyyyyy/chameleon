package cn.code.chameleon.enums;

/**
 * @author liumingyu
 * @create 2018-05-08 下午10:33
 */
public enum UserStatusEnum {

    OFFLINE(0, "离线"),
    ONLINE(1, "在线"),
    DISABLED(2, "禁用")
    ;

    private Integer code;

    private String desc;

    UserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    /**
     * 根据code获取desc
     *
     * @param code
     * @return
     */
    public static UserStatusEnum getUserTypeEnum(Integer code) {

        if (code == null) {
            return null;
        }
        UserStatusEnum returnEnum = null;
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (code.equals(userStatusEnum.getCode())) {
                returnEnum = userStatusEnum;
                break;
            }
        }
        return returnEnum;
    }
}
