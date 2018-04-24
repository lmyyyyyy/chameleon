package cn.code.chameleon.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author liumingyu
 * @create 2018-04-23 下午4:22
 */
public class WindowUtil {

    public static void scroll(WebDriver driver, int height) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + height + ");");
    }

    public static void loadAll(WebDriver driver) {
        int width = driver.manage().window().getSize().width;
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        long height = (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight;");
        driver.manage().window().setSize(new Dimension(width, (int) height));
        driver.navigate().refresh();
    }

    public static void taskScreenShot(WebDriver driver, File saveFile) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeWindow(WebDriver driver) {
        String handle = driver.getWindowHandle();
        for (String handles : driver.getWindowHandles()) {
            if (handles.equals(handle)) {
                continue;
            }
            driver.switchTo().window(handles);
        }
    }
}
