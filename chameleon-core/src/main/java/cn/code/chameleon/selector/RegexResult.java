package cn.code.chameleon.selector;

/**
 * @author liumingyu
 * @create 2018-04-13 下午12:02
 */
public class RegexResult {

    private String[] groups;

    public static final RegexResult EMTPY_RESULT = new RegexResult();

    public RegexResult() {
    }

    public RegexResult(String[] groups) {
        this.groups = groups;
    }

    public String get(int groupId) {
        if (groups == null) {
            return null;
        }
        return groups[groupId];
    }
}
