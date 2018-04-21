package cn.code.chameleon.downloader;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.selector.PlainText;
import cn.code.chameleon.utils.HttpConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author liumingyu
 * @create 2018-04-21 下午3:39
 */
public class PhantomJSDownloader extends AbstractDownloader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhantomJSDownloader.class);

    protected static final String REQUEST_FAILED = "HTTP request failed";

    private static String phantomJS = "phantomjs";

    private static String crawlJS = "crawl.js";

    private int retryTimes;

    private int threadNum;

    public PhantomJSDownloader() {
        init();
    }

    public PhantomJSDownloader(String phantomJS) {
        PhantomJSDownloader.phantomJS = phantomJS;
        init();
    }

    public PhantomJSDownloader(String phantomJS, String crawlJS) {
        PhantomJSDownloader.phantomJS = phantomJS;
        PhantomJSDownloader.crawlJS = crawlJS;
    }

    private void init() {
        PhantomJSDownloader.crawlJS = new File(this.getClass().getResource("/").getPath()).getPath() + System.getProperty("file.separator") + crawlJS;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public PhantomJSDownloader setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    protected String getPage(Request request) {
        try {
            String url = request.getUrl();
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(phantomJS + " " + crawlJS + " " + url);
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            LOGGER.error("{} phantomJS download page: {} error", Thread.currentThread().getName(), request.getUrl());
        }
        return null;
    }

    @Override
    public Page download(Request request, Task task) {
        LOGGER.info("{} phantomJS download page: {}", Thread.currentThread().getName(), request.getUrl());
        String content = getPage(request);
        Page page = new Page();
        if (content.contains(REQUEST_FAILED)) {
            for (int i = 0; i < getRetryTimes(); i++) {
                content = getPage(request);
                if (!content.contains(REQUEST_FAILED)) {
                    break;
                }
            }
            if (content.contains(REQUEST_FAILED)) {
                page.setRequest(request);
                return page;
            }
        }
        page.setRequest(request);
        page.setRawText(content);
        page.setUrl(new PlainText(request.getUrl()));
        page.setStatusCode(HttpConstant.StatusCode.CODE_200);
        return page;
    }

    @Override
    public void setThread(int threadNum) {
        this.threadNum = threadNum;
    }
}
