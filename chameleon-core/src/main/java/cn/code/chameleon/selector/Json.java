package cn.code.chameleon.selector;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-11 下午3:10
 */
public class Json extends PlainText {

    public Json(List<String> sourceTexts) {
        super(sourceTexts);
    }

    public Json(String text) {
        super(text);
    }

    @Override
    public Selectable jsonPath(String jsonPath) {
        JsonPathSelector jsonPathSelector = new JsonPathSelector(jsonPath);
        return selectList(jsonPathSelector, getSourceTexts());
    }
}
