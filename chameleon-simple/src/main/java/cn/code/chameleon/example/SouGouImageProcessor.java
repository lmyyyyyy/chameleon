package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.downloader.SeleniumDownloader;
import cn.code.chameleon.processor.PageProcessor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-28 下午12:18
 */
public class SouGouImageProcessor implements PageProcessor {

    private Site site = Site.init().setCycleRetryTimes(3).setSleepTime(3000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().regex("/pics/recommend\\?category=[\\s\\S]*&from=result").all());
        List<String> links = page.getHtml().regex("https://img\\d+\\.sogoucdn\\.com/app/a/\\d+/\\S+\\.jpg").all();
        //遍历links集合中的链接，然后下载
        for (int i = 0; i < links.size(); i++) {
            String link = links.get(i);
            try {
                URL url = new URL(link);
                URLConnection con = url.openConnection();
                InputStream inStream = con.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = inStream.read(buf)) != -1) {
                    outStream.write(buf, 0, len);
                }
                File file = new File("/data/chameleon/img/" + i + ".jpg");
                FileOutputStream op = new FileOutputStream(file);
                op.write(outStream.toByteArray());
                op.close();
                inStream.close();
                outStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        SeleniumDownloader seleniumDownloader = new SeleniumDownloader("Users/data/chameleon/chromedriver");
        seleniumDownloader.setThread(1);
        seleniumDownloader.setSleepTime(1000);
        Spider.create(new SouGouImageProcessor()).setDownload(seleniumDownloader).addUrls("http://pic.sogou.com/pics?query=%CD%BC%C6%AC&p=40230500&st=255&mode=255").thread(1).run();
    }
}
