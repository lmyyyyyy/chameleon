package cn.code.chameleon.robots;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author liumingyu
 * @create 2018-04-18 下午8:48
 */
public class PathRule {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private int type;

    public Pattern pattern;

    private static final Pattern EMPTY_PATTERN = Pattern.compile("^$");

    public PathRule(int type, String pattern) {
        this.type = type;
        this.pattern = robotsPattern2Regex(pattern);
    }

    public static Pattern robotsPattern2Regex(String pattern) {
        StringBuilder regex = new StringBuilder();
        StringBuilder quote = new StringBuilder();
        regex.append("^");
        boolean terminated = false;
        if (StringUtils.isEmpty(pattern)) {
            return EMPTY_PATTERN;
        }
        for (int pos = 0; pos < pattern.length(); ++pos) {
            char ch = pattern.charAt(pos);

            if (ch == '\\') {
                // Handle escaped * and $ characters
                char nch = pos < pattern.length() - 1 ? pattern.charAt(pos + 1) : 0;
                if (nch == '*' || ch == '$') {
                    quote.append(nch);
                    ++pos; // We need to skip one character
                } else {
                    quote.append(ch);
                }
            } else if (ch == '*') {
                // * indicates any sequence of one or more characters
                if (quote.length() > 0) {
                    // The quoted character buffer is not empty, so add them before adding
                    // the wildcard matcher
                    regex.append("\\Q").append(quote).append("\\E");
                    quote = new StringBuilder();
                }
                if (pos == pattern.length() - 1) {
                    terminated = true;
                    // A terminating * may match 0 or more characters
                    regex.append(".*");
                } else {
                    // A non-terminating * may match 1 or more characters
                    regex.append(".+");
                }
            } else if (ch == '$' && pos == pattern.length() - 1) {
                // A $ at the end of the pattern indicates that the path should end here in order
                // to match
                // This explicitly disallows prefix matching
                if (quote.length() > 0) {
                    // The quoted character buffer is not empty, so add them before adding
                    // the end-of-line matcher
                    regex.append("\\Q").append(quote).append("\\E");
                    quote = new StringBuilder();
                }
                regex.append(ch);
                terminated = true;
            } else {
                // Add the character as-is to the buffer for quoted characters
                quote.append(ch);
            }
        }

        // Add quoted string buffer: enclosed between \Q and \E
        if (quote.length() > 0) {
            regex.append("\\Q").append(quote).append("\\E");
        }

        // Add a wildcard pattern after the path to allow matches where this
        // pattern matches a prefix of the path.
        if (!terminated) {
            regex.append(".*");
        }

        // Return the compiled pattern
        return Pattern.compile(regex.toString());
    }

    public boolean matches(String path) {
        return this.pattern.matcher(path).matches();
    }

    public enum RuleEnums {

        USER_AGENT("user-agent"),
        ALLOW("allow"),
        DIS_ALLOW("disallow"),
        HOST("host"),
        SITE_MAP("sitemap"),
        CRAWL_DELAY("crawl-delay")
        ;

        private String rule;

        RuleEnums(String rule) {
            this.rule = rule;
        }

        public String getRule() {
            return rule;
        }

        public static RuleEnums getRuleEnumByValue(String value) {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            for (RuleEnums ruleEnums : RuleEnums.values()) {
                if (value.equals(ruleEnums.getRule())) {
                    return ruleEnums;
                }
            }
            return null;
        }

        public static Set<String> allRules() {
            Set<String> rules = new HashSet<>();
            for (RuleEnums ruleEnums : RuleEnums.values()) {
                if (ruleEnums != null) {
                    rules.add(ruleEnums.getRule());
                }
            }
            return rules;
        }
    }

    public int getType() {
        return type;
    }

}
