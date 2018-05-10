package cn.code.chameleon.monitor.enums;

/**
 * @author liumingyu
 * @create 2018-01-21 下午2:54
 */
public enum InvokeStatusEnum {

    PROCEEDING(0, "执行中"),
    SUCCESS(1, "成功"),
    FAIL(2, "失败"),
    UNKNOWN(-1, "未知状态");

    private int code;

    private String desc;

    InvokeStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
    public static InvokeStatusEnum getInvokeStatusEnum(Integer code) {

        if (code == null) {
            return null;
        }
        InvokeStatusEnum returnEnum = null;
        for (InvokeStatusEnum invokeStatusEnum : InvokeStatusEnum.values()) {
            if (code.equals(invokeStatusEnum.getCode())) {
                returnEnum = invokeStatusEnum;
                break;
            }
        }
        return returnEnum;
    }
}
