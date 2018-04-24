package cn.code.chameleon.downloader;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.selector.Html;
import cn.code.chameleon.selector.PlainText;
import cn.code.chameleon.selenium.WebDriverPool;
import cn.code.chameleon.utils.WindowUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-23 下午12:27
 */
public class SeleniumDownloader implements Downloader, Closeable {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private volatile WebDriverPool webDriverPool;

    private int sleepTime = 0;

    private int poolSize = 1;

    private static final String DRIVER_PHANTOMJS = "phantomjs";

    public SeleniumDownloader() {
    }

    public SeleniumDownloader(String chromeDriverPath) {
        System.getProperties().setProperty("webdriver.chrome.driver", chromeDriverPath);
    }

    private void checkInit() {
        if (webDriverPool == null) {
            synchronized (this) {
                webDriverPool = new WebDriverPool(poolSize);
            }
        }
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public SeleniumDownloader setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    @Override
    public Page download(Request request, Task task) {
        checkInit();
        WebDriver webDriver = null;
        Page page = new Page();
        try {
            webDriver = webDriverPool.get();
        } catch (InterruptedException e) {
            LOGGER.warn("{} Selenium get web driver from pool interrupted!", Thread.currentThread().getName(), e);
            page.addTargetRequest(request.getUrl());
            page.setJump(true);
            return page;
        } catch (Exception e) {
            LOGGER.error("{} Selenium get web driver from pool error!", Thread.currentThread().getName(), e);
            page.setJump(true);
            return page;
        }
        LOGGER.info("{} Selenium download page: {}", Thread.currentThread().getName(), request.getUrl());
        try {
            webDriver.get(request.getUrl());
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            LOGGER.error("Selenium downloader sleep interrupted!");
        } catch (Exception e) {
            LOGGER.error("Selenium download page: {} error", request.getUrl(), e);
            webDriverPool.close(webDriver);
            page.setJump(true);
            return page;
        }
        WebDriver.Options manage = webDriver.manage();
        Site site = task.getSite();
        if (site.getCookies() != null) {
            for (Map.Entry<String, String> cookieEntry : site.getCookies()
                    .entrySet()) {
                Cookie cookie = new Cookie(cookieEntry.getKey(),
                        cookieEntry.getValue());
                manage.addCookie(cookie);
            }
        }
        manage.window().maximize();
        WindowUtil.loadAll(webDriver);
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        String content = webElement.getAttribute("outerHTML");

        page.setRawText(content);
        page.setHtml(new Html(content, webDriver.getCurrentUrl()));
        page.setUrl(new PlainText(webDriver.getCurrentUrl()));
        page.setRequest(request);
        webDriverPool.returnPool(webDriver);
        return page;
    }

    @Override
    public void setThread(int threadNum) {
        this.poolSize = threadNum;
    }

    @Override
    public void close() throws IOException {
        webDriverPool.closeAll();
    }
}
