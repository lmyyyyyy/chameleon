package cn.code.chameleon.exception;

import cn.code.chameleon.enums.ResultCodeInterface;

/**
 * 自定义异常
 *
 * @author liumingyu
 * @create 2018-05-02 下午4:36
 */
public class ChameleonException extends Exception {

    private static final long serialVersionUID = 8333750150545190863L;

    private int code = -1;

    public ChameleonException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ChameleonException(ResultCodeInterface resultCodeInterface) {
        super(resultCodeInterface.getMsg());
        this.code = resultCodeInterface.getCode();
    }

    public ChameleonException(ResultCodeInterface resultCodeInterface, String message) {
        super(message);
        this.code = resultCodeInterface.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getName());
        sb.append(": (").append(code).append(")");

        String message = getLocalizedMessage();
        if (message != null) {
            sb.append(message);
        }

        return sb.toString();
    }
}
