package cn.code.chameleon.robots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author liumingyu
 * @create 2018-04-19 上午10:22
 */
public class UserAgentDirective {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public Set<String> userAgents;

    private List<String> siteMaps;

    private String preferHost;

    private Double crawlDelay;

    private Set<PathRule> pathRules = new HashSet<>();

    public UserAgentDirective(Set<String> userAgents) {
        this.userAgents = userAgents;
    }

    public UserAgentDirective(String userAgent) {
        if (this.userAgents == null) {
            this.userAgents = new HashSet<>();
        }
        this.userAgents.add(userAgent);
    }

    public Set<String> getUserAgents() {
        return userAgents;
    }

    public UserAgentDirective setUserAgents(Set<String> userAgents) {
        this.userAgents = userAgents;
        return this;
    }

    public List<String> getSiteMaps() {
        return siteMaps;
    }

    public UserAgentDirective setSiteMaps(List<String> siteMaps) {
        this.siteMaps = siteMaps;
        return this;
    }

    public UserAgentDirective addSiteMap(String siteMap) {
        if (this.siteMaps == null) {
            this.siteMaps = new ArrayList<>();
        }
        this.siteMaps.add(siteMap);
        return this;
    }

    public String getPreferHost() {
        return preferHost;
    }

    public UserAgentDirective setPreferHost(String preferHost) {
        this.preferHost = preferHost;
        return this;
    }

    public Double getCrawlDelay() {
        return crawlDelay;
    }

    public UserAgentDirective setCrawlDelay(Double crawlDelay) {
        this.crawlDelay = crawlDelay;
        return this;
    }

    public Set<PathRule> getPathRules() {
        return pathRules;
    }

    public UserAgentDirective setPathRules(Set<PathRule> pathRules) {
        this.pathRules = pathRules;
        return this;
    }

    public UserAgentDirective addPathRule(PathRule pathRule) {
        if (this.pathRules == null) {
            this.pathRules = new HashSet<>();
        }
        this.pathRules.add(pathRule);
        return this;
    }

    public void add(String rule, String value) {
        if (rule.equals(PathRule.RuleEnums.SITE_MAP.getRule())) {
            addSiteMap(value);
        } else if (rule.equals(PathRule.RuleEnums.CRAWL_DELAY.getRule())) {
            try {
                setCrawlDelay(Double.parseDouble(value));
            } catch (NumberFormatException e) {
                LOGGER.warn("Invalid number format for crawl-delay robots.txt: {}", value);
            }
        } else if (rule.equals(PathRule.RuleEnums.HOST.getRule())) {
            setPreferHost(value);
        } else if (rule.equals(PathRule.RuleEnums.ALLOW.getRule())) {
            addPathRule(new PathRule(HostDirective.ALLOWED, value));
        } else if (rule.equals(PathRule.RuleEnums.DIS_ALLOW.getRule())) {
            addPathRule(new PathRule(HostDirective.DISALLOWED, value));
        } else {
            LOGGER.error("Invalid key in robots.txt passed to UserAgentRules: {}", rule);
        }
    }

    static class PathComparator implements Comparator<PathRule> {

        String path;

        PathComparator(String path) {
            this.path = path;
        }

        @Override
        public int compare(PathRule o1, PathRule o2) {
            boolean o1Match = o1.matches(path);
            boolean o2Match = o2.matches(path);
            if (o1Match && !o2Match) {
                return -1;
            } else if (!o1Match && o2Match) {
                return 1;
            }
            String p1 = o1.pattern.toString();
            String p2 = o2.pattern.toString();
            if (p1.length() != p2.length()) {
                return Integer.compare(p2.length(), p1.length());
            }
            return p1.compareTo(p2);
        }
    }

    static class UserAgentComparator implements Comparator<UserAgentDirective> {

        String userAgent;

        UserAgentComparator(String userAgent) {
            this.userAgent = userAgent;
        }

        @Override
        public int compare(UserAgentDirective o1, UserAgentDirective o2) {
            int o1Match = o1.match(userAgent);
            int o2Match = o2.match(userAgent);
            if (o1Match != o2Match) {
                return Integer.compare(o2Match, o1Match);
            }
            if (o1.userAgents.size() != o2.userAgents.size()) {
                return Integer.compare(o1.userAgents.size(), o2.userAgents.size());
            }
            Iterator<String> iterator1 = o1.userAgents.iterator();
            Iterator<String> iterator2 = o2.userAgents.iterator();
            while (iterator1.hasNext()) {
                String temp1 = iterator1.next();
                String temp2 = iterator2.next();
                int order = temp1.compareTo(temp2);
                if (order != 0) {
                    return order;
                }
            }
            return 0;
        }
    }

    public int match(String userAgent) {
        userAgent = userAgent.toLowerCase();
        int max = 0;
        for (String ua : userAgents) {
            if (ua.equals("*") || userAgent.contains(ua)) {
                max = Math.max(max, ua.length());
            }
        }
        return max;
    }

    public boolean isWildCard() {
        return userAgents.contains("*");
    }

    public boolean isPathEmpty() {
        return pathRules.isEmpty();
    }

    public int checkAccess(String path, String userAgent) {
        if (match(userAgent) == 0) {
            return HostDirective.UNDEFINED;
        }
        Set<PathRule> rules = new TreeSet<>(new PathComparator(path));
        rules.addAll(pathRules);
        for (PathRule rule : rules) {
            if (rule.matches(path)) {
                return rule.getType();
            }
        }
        return HostDirective.UNDEFINED;
    }

}
