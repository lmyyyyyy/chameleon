package cn.code.chameleon.monitor.enums;

/**
 * @author liumingyu
 * @create 2018-01-21 下午3:01
 */
public enum EncodeTypeEnum {
    JSON(1,"json"),
    TOSTRING(2,"Object.toString()");

    private Integer code;

    private String desc;

    EncodeTypeEnum(Integer code, String desc) {
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
    public static EncodeTypeEnum getEncodeTypeEnum(Integer code) {

        if (code == null) {
            return null;
        }
        EncodeTypeEnum returnEnum = null;
        for (EncodeTypeEnum encodeTypeEnum : EncodeTypeEnum.values()) {
            if (code.equals(encodeTypeEnum.getCode())) {
                returnEnum = encodeTypeEnum;
                break;
            }
        }
        return returnEnum;
    }
}
