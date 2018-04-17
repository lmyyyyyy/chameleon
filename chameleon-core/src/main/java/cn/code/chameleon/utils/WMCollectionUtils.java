package cn.code.chameleon.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liumingyu
 * @create 2018-04-14 下午2:25
 */
public class WMCollectionUtils {

    public static <T> Set<T> newHashSet(T... t) {
        Set<T> set = new HashSet<>(t.length);
        for (T t0 : t) {
            set.add(t0);
        }
        return set;
    }

    public static <T> List<T> newArrayList (T... t) {
        List<T> list = new ArrayList<>(t.length);
        for (T t0 : t) {
            list.add(t0);
        }
        return list;
    }
}
