package cn.code.chameleon.selector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-13 下午4:56
 */
public class OrSelector implements Selector {

    private List<Selector> selectors = new ArrayList<>();

    public OrSelector(Selector... selectors) {
        for (Selector selector : selectors) {
            this.selectors.add(selector);
        }
    }

    public OrSelector(List<Selector> selectors) {
        this.selectors = selectors;
    }

    @Override
    public String select(String text) {
        if (text == null) {
            return null;
        }
        for (Selector selector : selectors) {
            String result = selector.select(text);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    @Override
    public List<String> selectList(String text) {
        List<String> results = new ArrayList<>();
        if (text == null) {
            return results;
        }
        for (Selector selector : selectors) {
            List<String> result = selector.selectList(text);
            results.addAll(result);
        }
        return results;
    }
}
