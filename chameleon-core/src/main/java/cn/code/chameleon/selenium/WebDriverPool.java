package cn.code.chameleon.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liumingyu
 * @create 2018-04-23 上午11:26
 */
public class WebDriverPool {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final int DEFAULT_CAPACITY = 5;

    private static final int STAT_RUNNING = 1;

    private static final int STAT_CLOSED = 2;

    private AtomicInteger stat = new AtomicInteger(STAT_RUNNING);

    private WebDriver webDriver;

    private int capacity;

    private AtomicInteger count = new AtomicInteger(0);

    protected static Properties properties;

    protected static DesiredCapabilities capabilities;

    private List<WebDriver> webDrivers = Collections.synchronizedList(new ArrayList<>());

    private BlockingDeque<WebDriver> innerQueue = new LinkedBlockingDeque<>(DEFAULT_CAPACITY);

    private String default_config_file = "/Users/liumingyu/data/chameleon/config.ini";

    private static final String DRIVER_FIREFOX = "firefox";

    private static final String DRIVER_CHROME = "chrome";

    private static final String DRIVER_PHANTOMJS = "phantomjs";

    private static final String SELENIUM_CONFIG = "selenium_config";

    private static final String PHANTOMJS_EXEC_PATH = "phantomjs_exec_path";

    private static final String PHANTOMJS_DRIVER_PATH = "phantomjs_driver_path";

    private static final String PHANTOMJS_DRIVER_LOGLEVEL = "phantomjs_driver_loglevel";

    public WebDriverPool(int capacity) {
        this.capacity = capacity;
        innerQueue = new LinkedBlockingDeque<>(capacity);
    }

    public WebDriverPool() {
        this(DEFAULT_CAPACITY);
    }

    public WebDriverPool(String configFile) {
        this(DEFAULT_CAPACITY);
        this.default_config_file = configFile;
    }

    public WebDriverPool(int capacity, String configFile) {
        this.capacity = capacity;
        this.default_config_file = configFile;
        innerQueue = new LinkedBlockingDeque<>(capacity);
    }

    public void configure() throws IOException {
        properties = new Properties();
        String configFile = default_config_file;
        if (System.getProperty(SELENIUM_CONFIG) != null) {
            configFile = System.getProperty(SELENIUM_CONFIG);
        }
        properties.load(new FileReader(configFile));

        capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("takesScreenshot", false);

        String driver = properties.getProperty("driver", DRIVER_PHANTOMJS);

        if (driver.equals(DRIVER_PHANTOMJS)) {
            if (properties.getProperty(PHANTOMJS_EXEC_PATH) != null) {
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, properties.getProperty(PHANTOMJS_EXEC_PATH));
            } else {
                throw new IOException(String.format("Property '%s' not set", PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY));
            }
            if (properties.getProperty(PHANTOMJS_DRIVER_PATH) != null) {
                capabilities.setCapability(
                        PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY,
                        properties.getProperty(PHANTOMJS_DRIVER_PATH));
            } else {
                LOGGER.info("Test will use PhantomJS internal GhostDriver");
            }
        }

        ArrayList<String> cliArgsCap = new ArrayList<String>();
        cliArgsCap.add("--web-security=false");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=true");
        cliArgsCap.add("--load-images=no");
        capabilities.setCapability(
                PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX
                        + "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
                cliArgsCap);

        // Control LogLevel for GhostDriver, via CLI arguments
        capabilities.setCapability(
                PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
                new String[]{"--logLevel="
                        + (properties.getProperty(PHANTOMJS_DRIVER_LOGLEVEL) != null ? properties
                        .getProperty(PHANTOMJS_DRIVER_LOGLEVEL)
                        : "INFO")});

        // Start appropriate Driver
        if (isUrl(driver)) {
            capabilities.setBrowserName(DRIVER_PHANTOMJS);
            webDriver = new RemoteWebDriver(new URL(driver), capabilities);
        } else if (driver.equals(DRIVER_FIREFOX)) {
            webDriver = new FirefoxDriver(capabilities);
        } else if (driver.equals(DRIVER_CHROME)) {
            webDriver = new ChromeDriver(capabilities);
        } else if (driver.equals(DRIVER_PHANTOMJS)) {
            webDriver = new PhantomJSDriver(capabilities);
        }
    }

    private boolean isUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public WebDriver get() throws InterruptedException {
        checkRunning();
        WebDriver poll = innerQueue.poll();
        if (poll != null) {
            return poll;
        }
        if (count.get() < capacity) {
            synchronized (innerQueue) {
                if (count.get() < capacity) {
                    try {
                        configure();
                        webDriver.manage().timeouts().pageLoadTimeout( 60, TimeUnit.SECONDS);
                        innerQueue.add(webDriver);
                        webDrivers.add(webDriver);
                        count.incrementAndGet();
                    } catch (IOException e) {
                        LOGGER.error("get web driver error {}", e);
                    }
                }
            }
        }
        return innerQueue.take();
    }

    public void returnPool(WebDriver webDriver) {
        checkRunning();
        innerQueue.add(webDriver);
    }

    protected void checkRunning() {
        if (!stat.compareAndSet(STAT_RUNNING, STAT_RUNNING)) {
            throw new IllegalStateException("WebDriver already closed!");
        }
    }

    public void closeAll() {
        boolean flag = stat.compareAndSet(STAT_RUNNING, STAT_CLOSED);
        if (!flag) {
            throw new IllegalStateException("WebDriver already closed!");
        }
        for (WebDriver webDriver : webDrivers) {
            LOGGER.info("Quit WebDriver " + webDriver);
            webDriver.close();
            webDriver.quit();
            count.decrementAndGet();
            webDriver = null;
        }
    }

    public void close(WebDriver webDriver) {
        webDriver.close();
        webDriver.quit();
        webDriver = null;
    }

    public void shutdown() {
        try {
            for (WebDriver driver : innerQueue) {
                close(driver);
            }
            innerQueue.clear();
        } catch (Exception e) {
            LOGGER.warn("web driver pool close failed!", e);
        }
    }
}
