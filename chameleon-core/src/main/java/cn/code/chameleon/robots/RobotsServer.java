package cn.code.chameleon.robots;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.downloader.HttpClientDownloader;
import cn.code.chameleon.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-09 下午3:17
 */
public class RobotsServer {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    protected RobotsConfig config;

    protected final Map<String, HostDirective> hostCache = new HashMap<>();

    public RobotsServer() {
        this.config = new RobotsConfig();
    }

    public RobotsServer(String userAgentName) {
        this.config = new RobotsConfig(userAgentName);
    }

    public RobotsServer(boolean ignoreUA) {
        this.config = new RobotsConfig(ignoreUA);
    }

    public RobotsServer(RobotsConfig config) {
        this.config = config;
    }

    public RobotsServer setIgnoreUA(boolean ignoreUA) {
        this.config.setIgnoreUADiscrimination(ignoreUA);
        return this;
    }

    public boolean allow(Request request, Task task) {
        if (!task.getSite().isObeyRobots()) {
            return true;
        }
        String host = task.getSite().getDomain().toLowerCase();
        String path = UrlUtils.getPath(request.getUrl());
        try {
            HostDirective hostDirective = hostCache.get(host);
            if (hostDirective != null && hostDirective.needRefetch()) {
                synchronized (hostCache) {
                    hostCache.remove(host);
                    hostDirective = null;
                }
            }
            if (hostDirective == null) {
                hostDirective = downloadDirective(request, task);
            }
            return hostDirective.allow(path, task);
        } catch (Exception e) {
            LOGGER.error("obey robots to validate url: {} error {}", request.getUrl(), e);
        }
        LOGGER.warn("observe some problems with obey robots, default allow url: {}", request.getUrl());
        return true;
    }

    private HostDirective downloadDirective(Request request, Task task) throws Exception {
        HostDirective hostDirective;
        request.setUrl(UrlUtils.getHost(request.getUrl()) + "/robots.txt");
        HttpClientDownloader downloader = new HttpClientDownloader();
        Page page = downloader.download(request, task);
        if (!page.isDownloadSuccess()) {
            LOGGER.error("download the robots page: {} error ", request.getUrl());
            throw new IllegalArgumentException("download the robots page: " + request.getUrl() + " error");
        }
        hostDirective = RobotsAnalyze.analyze(page.getRawText(), config);
        if (hostDirective == null) {
            hostDirective = new HostDirective(config);
        }
        synchronized (hostCache) {
            if (hostCache.size() == config.getCacheSize()) {
                String minHost = null;
                long minAccessTime = Long.MAX_VALUE;
                for (Map.Entry<String, HostDirective> entry : hostCache.entrySet()) {
                    long entryAccessTime = entry.getValue().getLastAccessTime();
                    if (entryAccessTime < minAccessTime) {
                        minAccessTime = entryAccessTime;
                        minHost = entry.getKey();
                    }
                }
                hostCache.remove(minHost);
            }
            hostCache.put(task.getSite().getDomain(), hostDirective);
        }
        return hostDirective;
    }
}
