package cn.code.chameleon.utils;

/**
 * @author liumingyu
 * @create 2018-04-11 下午5:54
 */
public class CompareUtils {

    public static int compareLong(long o1, long o2) {
        if (o1 < o2) {
            return -1;
        } else if (o1 == o2) {
            return 0;
        } else {
            return 1;
        }
    }
}
