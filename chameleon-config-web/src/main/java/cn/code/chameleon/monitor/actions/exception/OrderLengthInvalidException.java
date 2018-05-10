package cn.code.chameleon.monitor.actions.exception;

/**
 * @author liumingyu
 * @create 2018-01-21 下午4:56
 */
public class OrderLengthInvalidException extends RuntimeException {
    public OrderLengthInvalidException(String message) {
        super(message);
    }
}
