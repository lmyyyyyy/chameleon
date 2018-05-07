package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.utils.ImageUtil;
import cn.code.chameleon.utils.TesseractOcrUtil;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.downloader.SeleniumDownloader;
import cn.code.chameleon.entity.ImageRegion;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.selenium.SeleniumAction;
import cn.code.chameleon.utils.WindowUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author liumingyu
 * @create 2018-04-25 下午3:34
 */
public class MaoyanPageProcessor implements PageProcessor {

    private Site site = Site.init().setCycleRetryTimes(3).setSleepTime(3000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        if (page.getRequest().getUrl().matches("http[s]?://maoyan\\.com/")) {
            page.setJump(true);
        }
        page.addTargetRequests(page.getHtml().links().regex("http[s]?://maoyan\\.com/films/[\\d]+").all());
        page.putField("name", page.getHtml().xpath("//div[@class='movie-brief-container']/h3/text()"));
        page.putField("ename", page.getHtml().xpath("//div[@class='movie-brief-container']/div/text()"));
        page.putField("type", page.getHtml().xpath("//div[@class='movie-brief-container']/ul/li[1]/text()"));
        page.putField("date", page.getHtml().xpath("//div[@cl eass='movie-brief-container']/ul/li[3]/text()"));
        String temp = page.getHtml().xpath("//div[@class='movie-brief-container']/ul/li[2]/text()").get();
        if (temp != null && !"".equals(temp)) {
            String[] temps = temp.split("/");
            page.putField("country", temps[0]);
            page.putField("long", temps[1]);
        }
        page.putField("score", page.getHtml().xpath("//div[@class='movie-stats-container']/div/div/span/span/text()"));
        page.putField("money", page.getHtml().xpath("//div[@class='movie-stats-container']/div[2]/div/tidyText()"));
        page.putField("desc", page.getHtml().xpath("//div[@class='mod-content']/span/text()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void start() {
        Spider.create(new MaoyanPageProcessor()).setDownload(new SeleniumDownloader(new TestAction())).addUrls("http://maoyan.com/films/341624").addRequests().thread(5).run();
    }

    public static void main(String[] args) {
        //new MaoyanPageProcessor().start();
        Spider.create(new MaoyanPageProcessor()).addUrls("http://maoyan.com/films/341624").thread(5).run();
    }

    private class TestAction implements SeleniumAction {

        @Override
        public void execute(WebDriver driver) {
            WindowUtil.loadAll(driver);
            try {
                Thread.sleep(3000);
                //WebDriverWait wait = new WebDriverWait(driver, 10);
                //wait.until(ExpectedConditions.presenceOfElementLocated(By.id("J_PromoPriceNum")));

                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String srcfile = "/Users/liumingyu/data/" + UUID.randomUUID().toString() + ".png";
                FileUtils.copyFile(src, new File(srcfile));

                String pattern = "/html/body/div[3]/div/div[2]/div[3]/div[1]/div/span/span";
                WebElement tel = driver.findElement(By.xpath(pattern));

                Point loc = tel.getLocation();
                Dimension d = tel.getSize();
                String cop_path = "/Users/liumingyu/data/current_piaofang_" + UUID.randomUUID() + ".png";
                ImageUtil.crop(srcfile, cop_path, new ImageRegion(loc.x, loc.y, d.width + 10, d.height));
                System.out.println(TesseractOcrUtil.getByLangNum("/Users/liumingyu/data/a.png"));
                //System.out.println(TesseractOcrUtil.getByLangNum(cop_path));
                //FileUtils.forceDeleteOnExit(new File(srcfile));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
