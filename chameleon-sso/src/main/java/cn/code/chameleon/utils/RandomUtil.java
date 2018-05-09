package cn.code.chameleon.utils;

import java.util.Random;

/**
 * @author liumingyu
 * @create 2018-05-08 下午8:21
 */
public class RandomUtil {

    /**
     * 随机产生不含'0'和'O'字母数字组合字符串，长度由参数指定。
     *
     * @param length
     */
    public static String generateVerifyCode(int length) {
        String charList1 = "2345678923456789abcdefefghigkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
        StringBuffer rev = new StringBuffer();
        Random f = new Random();
        int count = 0;
        for (; count < length; count ++) {
            int ran = f.nextInt(65);
            rev.append(charList1.charAt(ran));
        }
        return rev.toString();
    }

    public static void main(String[] args) {
        System.out.println(RandomUtil.generateVerifyCode(new Random().nextInt(3) + 4));
    }
}
