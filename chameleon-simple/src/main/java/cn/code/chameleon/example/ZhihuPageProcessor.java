package cn.code.chameleon.example;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.downloader.PhantomJSDownloader;
import cn.code.chameleon.entity.AnswerInfo;
import cn.code.chameleon.pipeline.ConsolePipeline;
import cn.code.chameleon.pipeline.FilePipeline;
import cn.code.chameleon.processor.PageProcessor;
import cn.code.chameleon.selector.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-24 下午3:14
 */
public class ZhihuPageProcessor implements PageProcessor {

    private Site site = Site.init().setCycleRetryTimes(3).setSleepTime(3000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        if (page.getRequest().getUrl().matches("https://www\\.zhihu\\.com/")) {
            page.setJump(true);
        }
        page.addTargetRequests(page.getHtml().links().regex("/question/[\\d]+").all());
        String questionTitle = page.getHtml().xpath("//h1[@class='QuestionHeader-title']/text()").get();
        if (questionTitle == null || "".equals(questionTitle)) {
            page.setJump(true);
        }
        page.putField("questionTitle", questionTitle);
        page.putField("questionAboutCount", page.getHtml().xpath("//strong[@class='NumberBoard-itemValue']/text()"));
        //page.putField("questionViewCount", page.getHtml().xpath("//*[@id='root']/div/main/div/div[1]/div[2]/div[1]/div[2]/div/div/div/div/div/strong"));
        page.putField("questionAnswerCount", page.getHtml().xpath("//h4[@class='List-headerText']/span/text()"));
        page.putField("questionContent", page.getHtml().xpath("//div[@class='QuestionHeader-detail']/div/div/span[@class='RichText']/tidyText()"));
        List<String> temps;
        temps = page.getHtml().xpath("//div[@class='Popover']/div/text()").all();
        List<String> topics = new ArrayList<>();
        for (String str : temps) {
            if (str == null || "".equals(str.trim())) {
                continue;
            }
            topics.add(str);
        }
        page.putField("questionTopics", topics);
        List<String> answers = page.getHtml().xpath("//div[@class='List-item']").all();
        List<AnswerInfo> answerInfos = new ArrayList<>();
        for (String answer : answers) {
            Html html = new Html(answer);
            AnswerInfo answerInfo = new AnswerInfo();
            answerInfo.setAnswerAuthor(html.xpath("//div[@class='AuthorInfo-head']/span/div/div/a/text()").get());
            answerInfo.setAnswerComment(html.xpath("//div[@class='ContentItem-actions']/button[1]/text()").get());
            answerInfo.setAnswerVote(html.xpath("//span[@class='Voters']/button/text()").get());
            answerInfo.setAnswerDate(html.xpath("//div[@class='ContentItem-time']/a/span/text()").get());
            answerInfo.setAnswerContent(html.xpath("//div[@class='RichContent-inner']/span/tidyText()").get());
            answerInfos.add(answerInfo);
        }
        page.putField("answerInfos", answerInfos);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        PhantomJSDownloader phantomDownloader =
                new PhantomJSDownloader("/Users/liumingyu/data/chameleon/phantomjs-2.1.1-macosx/bin/phantomjs"
                        , "/Users/liumingyu/data/chameleon/crawl.js").setRetryTimes(3);
        Spider.create(new ZhihuPageProcessor()).setDownload(phantomDownloader).addPipeline(new ConsolePipeline()).addPipeline(new FilePipeline()).addUrls("https://www.zhihu.com/question/264395357").thread(5).run();
    }
}
