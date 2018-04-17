package cn.code.chameleon.selector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-13 下午4:56
 */
public class AndSelector implements Selector {

    private List<Selector> selectors = new ArrayList<>();

    public AndSelector(Selector... selectors) {
        for (Selector selector : selectors) {
            this.selectors.add(selector);
        }
    }

    public AndSelector(List<Selector> selectors) {
        this.selectors = selectors;
    }

    @Override
    public String select(String text) {
        for (Selector selector : selectors) {
            if (text == null) {
                return null;
            }
            text = selector.select(text);
        }
        return text;
    }

    @Override
    public List<String> selectList(String text) {
        List<String> results = new ArrayList<>();
        boolean flag = true;
        for (Selector selector : selectors) {
            if (flag) {
                results = selector.selectList(text);
                flag = false;
            } else {
                List<String> resultTemp = new ArrayList<>();
                for (String result : results) {
                    resultTemp.addAll(selector.selectList(result));
                }
                results = resultTemp;
                if (results == null || results.isEmpty()) {
                    return results;
                }
            }
        }
        return results;
    }
}
