package cn.code.chameleon.robots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liumingyu
 * @create 2018-04-09 下午3:15
 */
public class RobotsAnalyze {

    private static final Logger LOGGER = LoggerFactory.getLogger(RobotsAnalyze.class);

    private static final Pattern RULE_PATTERN = Pattern.compile("(?i)^([A-Za-z\\-]+):(.*)");

    private static Set<String> valid_rules;

    static {
        valid_rules = PathRule.RuleEnums.allRules();
    }

    public static HostDirective analyze(String content, RobotsConfig config) {
        HostDirective hostDirective = new HostDirective(config);
        StringTokenizer tokenizer = new StringTokenizer(content, "\n\r");
        Set<String> userAgents = new HashSet<>();
        UserAgentDirective userAgentDirective = null;

        while (tokenizer.hasMoreTokens()) {
            String line = tokenizer.nextToken();
            if (replaceLine(line).isEmpty()) {
                continue;
            }
            Matcher matcher = RULE_PATTERN.matcher(line);
            if (matcher.matches()) {
                String rule = matcher.group(1).toLowerCase();
                String value = matcher.group(2).trim();

                if (valid_rules.contains(rule)) {
                    if (rule.equals(PathRule.RuleEnums.USER_AGENT.getRule())) {
                        String currentUserAgent = value.toLowerCase();
                        if (userAgentDirective != null) {
                            userAgents = new HashSet<>();
                            hostDirective.addRule(userAgentDirective);
                            userAgentDirective = null;
                        }
                        userAgents.add(currentUserAgent);
                    } else {
                        if (userAgentDirective == null) {
                            if (userAgents.isEmpty()) {
                                userAgents.add("*");
                            }
                            userAgentDirective = new UserAgentDirective(userAgents);
                        }
                        userAgentDirective.add(rule, value);
                    }
                } else {
                    LOGGER.info("Unrecognized rule in robots.txt: {}", rule);
                }
            } else {
                LOGGER.debug("Unrecognized line in robots.txt: {}", line);
            }
        }
        if (Objects.nonNull(userAgentDirective)) {
            hostDirective.addRule(userAgentDirective);
        }
        return hostDirective;
    }

    public static String replaceLine(String line) {
        int commentIndex = line.indexOf('#');
        if (commentIndex > -1) {
            line = line.substring(0, commentIndex);
        }

        // remove any html markup
        line = line.replaceAll("<[^>]+>", "").trim();
        return line;
    }
}
