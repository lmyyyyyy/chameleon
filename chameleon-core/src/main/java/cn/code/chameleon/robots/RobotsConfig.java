package cn.code.chameleon.robots;

/**
 * @author liumingyu
 * @create 2018-04-09 下午3:14
 */
public class RobotsConfig {

    private String userAgentName = "chameleon";

    private boolean ignoreUADiscrimination = true;

    private int cacheSize = 500;

    public RobotsConfig() {
    }

    public RobotsConfig(String userAgentName) {
        this.userAgentName = userAgentName;
    }

    public RobotsConfig(boolean ignoreUADiscrimination) {
        this.ignoreUADiscrimination = ignoreUADiscrimination;
    }

    public RobotsConfig(String userAgentName, boolean ignoreUADiscrimination) {
        this.userAgentName = userAgentName;
        this.ignoreUADiscrimination = ignoreUADiscrimination;
    }

    public RobotsConfig(String userAgentName, boolean ignoreUADiscrimination, int cacheSize) {
        this.userAgentName = userAgentName;
        this.ignoreUADiscrimination = ignoreUADiscrimination;
        this.cacheSize = cacheSize;
    }

    public String getUserAgentName() {
        return userAgentName;
    }

    public RobotsConfig setUserAgentName(String userAgentName) {
        this.userAgentName = userAgentName;
        return this;
    }

    public boolean isIgnoreUADiscrimination() {
        return ignoreUADiscrimination;
    }

    public RobotsConfig setIgnoreUADiscrimination(boolean ignoreUADiscrimination) {
        this.ignoreUADiscrimination = ignoreUADiscrimination;
        return this;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public RobotsConfig setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
        return this;
    }
}
