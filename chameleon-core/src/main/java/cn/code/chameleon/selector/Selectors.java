package cn.code.chameleon.selector;

/**
 * @author liumingyu
 * @create 2018-04-11 下午3:06
 */
public abstract class Selectors {

    public static RegexSelector regex(String expr) {
        return new RegexSelector(expr);
    }

    public static RegexSelector regex(String expr, int group) {
        return new RegexSelector(expr, group);
    }

    public static SmartContentSelector smartContent() {
        return new SmartContentSelector();
    }

    public static CssSelector $(String expr) {
        return new CssSelector(expr);
    }

    public static CssSelector $(String expr, String attrName) {
        return new CssSelector(expr, attrName);
    }

    public static XpathSelector xpathSelector(String expr) {
        return new XpathSelector(expr);
    }

    public static AndSelector andSelector(Selector... selectors) {
        return new AndSelector(selectors);
    }

    public static OrSelector orSelector(Selector... selectors) {
        return new OrSelector(selectors);
    }
}
