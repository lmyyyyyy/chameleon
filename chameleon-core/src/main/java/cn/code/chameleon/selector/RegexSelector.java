package cn.code.chameleon.selector;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author liumingyu
 * @create 2018-04-13 上午11:27
 */
public class RegexSelector implements Selector {

    private String regexStr;

    private Pattern regex;

    private int group = 1;

    public RegexSelector(String regexStr) {
        this.compileRegex(regexStr);
        if (regex.matcher("").groupCount() == 0) {
            group = 0;
        } else {
            group = 1;
        }
    }

    public RegexSelector(String regexStr, int group) {
        this.compileRegex(regexStr);
        this.group = group;
    }

    private void compileRegex(String regexStr) {
        if (StringUtils.isBlank(regexStr)) {
            throw new IllegalArgumentException("regex must not be empty");
        }
        try {
            this.regex = Pattern.compile(regexStr, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            this.regexStr = regexStr;
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("illegal regex to syntax error: ", e);
        }
    }

    @Override
    public String select(String text) {
        return selectGroup(text).get(group);
    }

    @Override
    public List<String> selectList(String text) {
        List<String> strings = new ArrayList<>();
        List<RegexResult> results = selectGroupList(text);
        for (RegexResult regexResult : results) {
            strings.add(regexResult.get(group));
        }
        return strings;
    }

    public RegexResult selectGroup(String text) {
        Matcher matcher = regex.matcher(text);
        if (matcher.find()) {
            String[] groups = new String[matcher.groupCount() + 1];
            for (int i = 0; i < groups.length; i ++) {
                groups[i] = matcher.group(i);
            }
            return new RegexResult(groups);
        }
        return RegexResult.EMTPY_RESULT;
    }

    public List<RegexResult> selectGroupList(String text) {
        Matcher matcher = regex.matcher(text);
        List<RegexResult> results = new ArrayList<>();
        while (matcher.find()) {
            String[] groups = new String[matcher.groupCount() + 1];
            for (int i = 0; i < groups.length; i ++) {
                groups[i] = matcher.group(i);
            }
            results.add(new RegexResult(groups));
        }
        return results;
    }

    @Override
    public String toString() {
        return "RegexSelector{" +
                "regexStr='" + regexStr + '\'' +
                ", group=" + group +
                '}';
    }
}
