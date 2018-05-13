package cn.code.chameleon.model;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-05-12 下午1:50
 */
@Data
@ToString
public class SpiderTemplate implements Serializable {

    private static final long serialVersionUID = 8744150390625369950L;

    /** 域名 */
    private String domain;

    /** 站点名称 */
    private String siteName;

    /** 编码 */
    private String charset;

    /** 是否更新之前的数据 该功能会清空所有的url队列缓存和去重缓存 */
    private boolean isUpdate = false;

    /** 线程数量 */
    private int thread = 1;

    /** 睡眠时间 */
    private int sleepTime = 1000;

    /** 重试次数 */
    private int retryTimes = 1;

    /** 重试睡眠时间 */
    private int retrySleepTime = 1000;

    /** 爬取的最大页面数 默认-1不限制 */
    private long maxCount = -1;

    /** 超时时间 */
    private int timeout = 5000;

    /** 遵守robot协议 */
    private boolean obeyRobot = false;

    /** 请求头列表 */
    private Map<String, String> headers;

    /** cookies */
    private Map<String, Map<String, String>> cookies;

    /** 是否使用智能爬取器 */
    private boolean isSmartCrawler = false;

    /** 开始url列表 */
    private List<String> startUrls;

    /** 目标url相关列表 */
    private List<TargetUrl> targetUrls;

    /** 选用的调度器 */
    private int scheduler;

    /** 选择的url去重器 */
    private int duplicateRemover = 1;

    /** 选择的下载器 */
    private int downloader = 1;

    /** 选择的管道列表 */
    private List<Integer> pipelines;

    /** 是否使用代理IP */
    private boolean isProxyIp = false;

    /** 设置这些格式的url跳过不解析 只支持正则 */
    private List<String> jumpUrls;

    /** 是否是集合的方式 */
    private boolean isCollection = false;

    /** 集合元素解析格式 */
    private String collectionPattern;

    /** 动态字段 */
    private List<DynamicField> dynamicFields = Lists.newArrayList();

    @Data
    @ToString
    public class TargetUrl implements Serializable {

        private static final long serialVersionUID = 981379591601416016L;

        /** 目标url的格式类型 regex；css；xpath */
        private int targetUrlType = 1;

        /** 目标url的格式 */
        private String targetUrlPattern;

        /** 优先级 */
        private long priority = 0;
    }

    @Data
    @ToString
    public class DynamicField implements Serializable {

        private static final long serialVersionUID = 3267092337192749814L;

        /** 动态字段key */
        private String key;

        /** 正则表达式 */
        private String regex;

        /** xpath表达式 */
        private String xpath;

        /** JsonPath表达式 */
        private String jsonPath;

        /** css表达式 */
        private String css;

        /** css选择的属性 */
        private String property;
    }

}
