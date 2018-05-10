package cn.code.chameleon.enums;

/**
 * @author liumingyu
 * @create 2018-05-07 下午2:39
 */
public enum ResultCodeEnum implements ResultCodeInterface {

    SUCCESS(SUCCESS_CODE, "成功"),
    FAILED(FAIL_CODE, "失败"),

    USER_DATA_EMPTY(1001, "用户对象不能为空"),
    USER_PASSWORD_LEN_ERROR(1002, "密码长度错误"),
    USER_ACCOUNT_PASSWORD_ERROR(1003, "账号或密码错误"),
    USER_NAME_CAN_NOT_EMPTY(1004, "用户昵称不能为空"),
    USER_ACCOUNT_PATTERN_ERROR(1005, "账号格式错误"),
    USER_ACCOUNT_HAS_EXISTED(1006, "账号已经存在"),
    USER_PASSWORD_MD5_FAILED(1007, "密码加密失败,请重试或更换密码"),

    ROLE_DATA_EMPTY(2001, "角色对象不能为空"),
    ROLE_NAME_EMPTY(2002, "角色名称不能为空"),

    FUNCTION_DATA_EMPTY(3001, "功能对象不能为空"),
    FUNCTION_NAME_EMPTY(3002, "功能名称不能为空"),
    FUNCTION_CODE_EXIST(3003, "功能标识已被占用"),

    NO_FIND_CURRENT_USER(9001, "未获取到当前用户,或当前用户未登录"),
    ACTION_CONTEXT(9002, "ActionContext对象为空"),
    TOKEN_IS_OUT_TIME(9003, "会话超时"),
    COOKIE_IS_OUT_TIME(9004, "Cookie失效"),
    INPUT_ERROR(9005, "参数异常")
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
