package cn.code.chameleon.selector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-11 下午3:25
 */
public class PlainText extends AbstractSelectable {

    protected List<String> sourceTexts;

    public PlainText(List<String> souceTexts) {
        this.sourceTexts = souceTexts;
    }

    public PlainText(String text) {
        this.sourceTexts = new ArrayList<>();
        sourceTexts.add(text);
    }

    public static PlainText create(String text) {
        return new PlainText(text);
    }

    @Override
    protected List<String> getSourceTexts() {
        return sourceTexts;
    }

    @Override
    public Selectable $(String selector) {
        throw new UnsupportedOperationException("$ can not apply to PlainText");
    }

    @Override
    public Selectable $(String selector, String attrName) {
        throw new UnsupportedOperationException("$ can not apply to PlainText");
    }

    @Override
    public Selectable links() {
        throw new UnsupportedOperationException("Links can not apply to PlainText");
    }

    @Override
    public Selectable smartContent() {
        throw new UnsupportedOperationException("SmartContent can not apply to PlainText");
    }

    @Override
    public Selectable xpath(String xpath) {
        throw new UnsupportedOperationException("Xpath can not apply to PlainText");
    }

    @Override
    public List<Selectable> nodes() {
        List<Selectable> selectables = new ArrayList<>(sourceTexts.size());
        for (String str : getSourceTexts()) {
            selectables.add(PlainText.create(str));
        }
        return selectables;
    }
}
