package cn.code.chameleon.robots;

import cn.code.chameleon.carrier.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * @author liumingyu
 * @create 2018-04-19 上午11:17
 */
public class HostDirective {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private long expiredTime = -1;

    public static final int ALLOWED = 1;

    public static final int DISALLOWED = 2;

    public static final int UNDEFINED = 3;

    private Set<UserAgentDirective> rules;

    private long lastFetchRobotsTime;

    private long lastAccessTime;

    private RobotsConfig config;

    private String userAgent;

    public HostDirective(RobotsConfig config) {
        this.lastFetchRobotsTime = System.currentTimeMillis();
        this.config = config;
        this.userAgent = config.getUserAgentName().toLowerCase();
        this.rules = new TreeSet<>(new UserAgentDirective.UserAgentComparator(userAgent));
    }

    public boolean needRefetch() {
        if (expiredTime <= 0) {
            expiredTime = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
        }
        return (System.currentTimeMillis() - lastFetchRobotsTime) > expiredTime;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public HostDirective setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
        return this;
    }

    public HostDirective setExpiredTime(long sourceDuration, TimeUnit sourceUnit) {
        this.expiredTime = TimeUnit.MILLISECONDS.convert(sourceDuration, sourceUnit);
        return this;
    }

    public Set<UserAgentDirective> getRules() {
        return rules;
    }

    public HostDirective setRules(Set<UserAgentDirective> rules) {
        this.rules = rules;
        return this;
    }

    public HostDirective addRule(UserAgentDirective rule) {
        this.rules.add(rule);
        return this;
    }

    public long getLastFetchRobotsTime() {
        return lastFetchRobotsTime;
    }

    public HostDirective setLastFetchRobotsTime(long lastFetchRobotsTime) {
        this.lastFetchRobotsTime = lastFetchRobotsTime;
        return this;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public HostDirective setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
        return this;
    }

    public RobotsConfig getConfig() {
        return config;
    }

    public HostDirective setConfig(RobotsConfig config) {
        this.config = config;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public HostDirective setUserAgent(String userAgent) {
        this.userAgent = userAgent.toLowerCase();
        Set<UserAgentDirective> replace = new TreeSet<>(new UserAgentDirective.UserAgentComparator(this.userAgent));
        replace.addAll(rules);
        rules = replace;
        return this;
    }

    public int checkAccess(String path, Task task) {
        lastAccessTime = System.currentTimeMillis();
        int result = UNDEFINED;
        String userAgent = config.getUserAgentName();
        boolean ignoreUADisc = config.isIgnoreUADiscrimination();

        for (UserAgentDirective rule : rules) {
            int score = rule.match(userAgent);

            if (score == 0 && !ignoreUADisc) {
                break;
            }
            result = rule.checkAccess(path, this.userAgent);
            if (result != DISALLOWED || (!rule.isWildCard() || !ignoreUADisc)) {
                if (rule.getCrawlDelay() != null && rule.getCrawlDelay() > 0) {
                    int siteSleep = task.getSite().getSleepTime();
                    try {
                        if (siteSleep >= rule.getCrawlDelay()) {
                            Thread.sleep(rule.getCrawlDelay().intValue());
                        } else {
                            Thread.sleep(siteSleep);
                        }
                    } catch (InterruptedException e) {
                        LOGGER.error("obey robots crawl delay thread interrupt sleep error");
                    }
                }
                break;
            }
        }
        return result;
    }

    public boolean allow(String path, Task task) {
        return checkAccess(path, task) != DISALLOWED;
    }

    public boolean disallow(String path, Task task) {
        return checkAccess(path, task) == DISALLOWED;
    }
}
