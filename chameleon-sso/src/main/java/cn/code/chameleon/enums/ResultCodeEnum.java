package cn.code.chameleon.enums;

/**
 * @author liumingyu
 * @create 2018-05-08 下午7:42
 */
public enum ResultCodeEnum implements ResultCodeInterface {

    SUCCESS(SUCCESS_CODE, "成功"),
    FAILED(FAIL_CODE, "失败"),

    USER_DATA_EMPTY(1001, "用户对象不能为空"),
    USER_ACCOUNT_PATTERN_ERROR(1002, "用户账号格式不正确"),
    USER_PASSWORD_MD5_FAILED(1003, "密码加密失败,请重试或更换密码"),
    USER_PASSWORD_LEN_ERROR(1004, "密码长度错误"),
    USER_ACCOUNT_NOT_EXIST(1005, "用户账号错误"),
    USER_ACCOUNT_HAS_EXISTED(1006, "用户账号已存在"),
    USER_ACCOUNT_PASSWORD_ERROR(1007, "账号或密码错误"),
    USER_NAME_CAN_NOT_EMPTY(1008, "用户昵称不能为空"),

    SEND_EMAIL_FAILED(9001, "获取验证码失败,请稍后再试~"),
    VERIFY_CODE_VALIDATE_FAILED(9002, "验证码不正确"),
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
