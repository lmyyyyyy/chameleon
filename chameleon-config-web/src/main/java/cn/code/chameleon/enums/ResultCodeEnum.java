package cn.code.chameleon.enums;

/**
 * @author liumingyu
 * @create 2018-05-07 下午2:39
 */
public enum ResultCodeEnum implements ResultCodeInterface {

    SUCCESS(SUCCESS_CODE, "成功"),
    FAILED(FAIL_CODE, "失败"),

    USER_DATA_EMPTY(1001, "用户对象不能为空"),
    USER_PASSWORD_LEN_ERROR(1004, "密码长度错误"),
    USER_ACCOUNT_PASSWORD_ERROR(1007, "账号或密码错误"),
    USER_NAME_CAN_NOT_EMPTY(1008, "用户昵称不能为空"),

    ROLE_DATA_EMPTY(2001, "角色对象不能为空"),
    ROLE_NAME_EMPTY(2002, "角色名称不能为空"),

    FUNCTION_DATA_EMPTY(3001, "功能对象不能为空"),
    FUNCTION_NAME_EMPTY(3002, "功能名称不能为空"),
    FUNCTION_CODE_EXIST(3003, "功能标识已被占用"),

    TOKEN_IS_OUT_TIME(9003, "会话超时"),
    COOKIE_IS_OUT_TIME(9004, "Cookie失效")
    ;

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
