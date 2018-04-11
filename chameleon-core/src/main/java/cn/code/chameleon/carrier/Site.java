package cn.code.chameleon.carrier;

import cn.code.chameleon.utils.HttpConstant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author liumingyu
 * @create 2018-04-09 下午3:40
 */
public class Site implements Serializable {

    private static final long serialVersionUID = 1818129625225201467L;

    private String domain;

    private String userAgent;

    private String charset;

    private Map<String, String> defaultCookies = new LinkedHashMap<>();

    private Map<String, Map<String, String>> cookies = new HashMap<>();

    private int sleepTime;

    private int retryTimes;

    private int cycleRetryTimes;

    private int retrySleepTime;

    private int timeOut;

    private static final Set<Integer> DEFAULT_STATUS_CODE_SET = new HashSet<>();

    private Set<Integer> acceptStatusCode = DEFAULT_STATUS_CODE_SET;

    private Map<String, String> heads = new HashMap<>();

    private boolean useGzip = true;

    private boolean disableCookies;

    static {
        DEFAULT_STATUS_CODE_SET.add(HttpConstant.StatusCode.CODE_200);
    }

    public static Site init() {
        return new Site();
    }

    public Site addCookie(String name, String value) {
        defaultCookies.put(name, value);
        return this;
    }

    public Site addCookie(String domain, String name, String value) {
        if (!cookies.containsKey(domain)) {
            cookies.put(domain, new HashMap<>());
        }
        cookies.get(domain).put(name, value);
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public Site setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Site setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public Site setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public Map<String, String> getDefaultCookies() {
        return defaultCookies;
    }

    public Site setDefaultCookies(Map<String, String> defaultCookies) {
        this.defaultCookies = defaultCookies;
        return this;
    }

    public Map<String, Map<String, String>> getAllCookies() {
        return cookies;
    }

    public Site setCookies(Map<String, Map<String, String>> cookies) {
        this.cookies = cookies;
        return this;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public Site setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public Site setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    public int getCycleRetryTimes() {
        return cycleRetryTimes;
    }

    public Site setCycleRetryTimes(int cycleRetryTimes) {
        this.cycleRetryTimes = cycleRetryTimes;
        return this;
    }

    public int getRetrySleepTime() {
        return retrySleepTime;
    }

    public Site setRetrySleepTime(int retrySleepTime) {
        this.retrySleepTime = retrySleepTime;
        return this;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public Site setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public static Set<Integer> getDefaultStatusCodeSet() {
        return DEFAULT_STATUS_CODE_SET;
    }

    public Set<Integer> getAcceptStatusCode() {
        return acceptStatusCode;
    }

    public Site setAcceptStatusCode(Set<Integer> acceptStatusCode) {
        this.acceptStatusCode = acceptStatusCode;
        return this;
    }

    public Map<String, String> getHeads() {
        return heads;
    }

    public Site setHeads(Map<String, String> heads) {
        this.heads = heads;
        return this;
    }

    public boolean isUseGzip() {
        return useGzip;
    }

    public Site setUseGzip(boolean useGzip) {
        this.useGzip = useGzip;
        return this;
    }

    public boolean isDisableCookies() {
        return disableCookies;
    }

    public Site setDisableCookies(boolean disableCookies) {
        this.disableCookies = disableCookies;
        return this;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        if (sleepTime != site.sleepTime) return false;
        if (retryTimes != site.retryTimes) return false;
        if (cycleRetryTimes != site.cycleRetryTimes) return false;
        if (retrySleepTime != site.retrySleepTime) return false;
        if (timeOut != site.timeOut) return false;
        if (useGzip != site.useGzip) return false;
        if (disableCookies != site.disableCookies) return false;
        if (!domain.equals(site.domain)) return false;
        if (!userAgent.equals(site.userAgent)) return false;
        if (!charset.equals(site.charset)) return false;
        if (defaultCookies != null ? !defaultCookies.equals(site.defaultCookies) : site.defaultCookies != null)
            return false;
        if (cookies != null ? !cookies.equals(site.cookies) : site.cookies != null) return false;
        if (acceptStatusCode != null ? !acceptStatusCode.equals(site.acceptStatusCode) : site.acceptStatusCode != null)
            return false;
        return heads.equals(site.heads);
    }

    @Override
    public int hashCode() {
        int result = domain.hashCode();
        result = 31 * result + userAgent.hashCode();
        result = 31 * result + charset.hashCode();
        result = 31 * result + (defaultCookies != null ? defaultCookies.hashCode() : 0);
        result = 31 * result + (cookies != null ? cookies.hashCode() : 0);
        result = 31 * result + sleepTime;
        result = 31 * result + retryTimes;
        result = 31 * result + cycleRetryTimes;
        result = 31 * result + retrySleepTime;
        result = 31 * result + timeOut;
        result = 31 * result + (acceptStatusCode != null ? acceptStatusCode.hashCode() : 0);
        result = 31 * result + heads.hashCode();
        result = 31 * result + (useGzip ? 1 : 0);
        result = 31 * result + (disableCookies ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Site{" +
                "domain='" + domain + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", charset='" + charset + '\'' +
                ", defaultCookies=" + defaultCookies +
                ", cookies=" + cookies +
                ", sleepTime=" + sleepTime +
                ", retryTimes=" + retryTimes +
                ", cycleRetryTimes=" + cycleRetryTimes +
                ", retrySleepTime=" + retrySleepTime +
                ", timeOut=" + timeOut +
                ", acceptStatusCode=" + acceptStatusCode +
                ", heads=" + heads +
                ", useGzip=" + useGzip +
                ", disableCookies=" + disableCookies +
                '}';
    }
}
