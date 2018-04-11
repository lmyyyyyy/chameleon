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
}
