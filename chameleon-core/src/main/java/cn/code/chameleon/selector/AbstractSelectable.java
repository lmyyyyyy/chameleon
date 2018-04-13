package cn.code.chameleon.selector;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-11 下午3:08
 */
public abstract class AbstractSelectable implements Selectable {

    @Override
    public Selectable css(String selector) {
        return $(selector);
    }

    @Override
    public Selectable css(String selector, String attrName) {
        return $(selector, attrName);
    }

    @Override
    public Selectable regex(String regex) {
        RegexSelector regexSelector = new RegexSelector(regex);
        return selectList(regexSelector, getSourceTexts());
    }

    @Override
    public Selectable regex(String regex, int group) {
        RegexSelector regexSelector = new RegexSelector(regex, group);
        return selectList(regexSelector, getSourceTexts());
    }

    @Override
    public Selectable replace(String regex, String replacement) {
        ReplaceSelector replaceSelector = new ReplaceSelector(regex, replacement);
        return select(replaceSelector, getSourceTexts());
    }

    @Override
    public Selectable jsonPath(String jsonPath) {
        throw new UnsupportedOperationException();
    }

    protected abstract List<String> getSourceTexts();

    public String getFirstSourceText() {
        if (getSourceTexts() != null && getSourceTexts().size() > 0) {
            return getSourceTexts().get(0);
        }
        return null;
    }

    protected Selectable select(Selector selector, List<String> strings) {
        List<String> results = new ArrayList<>();
        for (String str : strings) {
            String result = selector.select(str);
            if (result != null) {
                results.add(result);
            }
        }
        return new PlainText(results);
    }

    protected Selectable selectList(Selector selector, List<String> strings) {
        List<String> results = new ArrayList<>();
        for (String str : strings) {
            List<String> result = selector.selectList(str);
            results.addAll(result);
        }
        return new PlainText(results);
    }

    @Override
    public Selectable select(Selector selector) {
        return select(selector, getSourceTexts());
    }

    @Override
    public Selectable selectList(Selector selector) {
        return selectList(selector, getSourceTexts());
    }

    @Override
    public String toString() {
        return get();
    }

    @Override
    public String get() {
        if (CollectionUtils.isNotEmpty(all())) {
            return all().get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean isMatch() {
        return getSourceTexts() != null && !getSourceTexts().isEmpty();
    }

    @Override
    public List<String> all() {
        return getSourceTexts();
    }
}
