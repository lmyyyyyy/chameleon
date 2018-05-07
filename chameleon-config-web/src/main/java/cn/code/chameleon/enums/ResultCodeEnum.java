package cn.code.chameleon.enums;

/**
 * @author liumingyu
 * @create 2018-05-07 下午2:39
 */
public enum ResultCodeEnum implements ResultCodeInterface {

    SUCCESS(SUCCESS_CODE, "成功"),
    FAILED(FAIL_CODE, "失败");

    private int code;

    private String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultCodeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ResultCodeEnum codeEnum : values()) {
            if (codeEnum.getCode() == code.intValue()) {
                return codeEnum;
            }
        }
        return null;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return message;
    }
}
