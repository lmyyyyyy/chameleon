package cn.code.chameleon.enums;

/**
 * 结果状态码接口
 *
 * @author liumingyu
 * @create 2018-05-02 下午4:44
 */
public interface ResultCodeInterface {

    int SUCCESS_CODE = 0;

    int FAIL_CODE = 500;

    String SUCCESS_MSG = "成功";

    int getCode();

    String getMsg();
}
