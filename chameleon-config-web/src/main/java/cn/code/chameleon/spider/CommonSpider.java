package cn.code.chameleon.spider;

import cn.code.chameleon.Spider;
import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.model.SpiderTemplate;
import cn.code.chameleon.processor.PageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liumingyu
 * @create 2018-05-12 下午8:48
 */
public class CommonSpider extends Spider {

    private final Logger LOGGER = LoggerFactory.getLogger(CommonSpider.class);

    private SpiderTemplate spiderTemplate;

    public CommonSpider(PageProcessor pageProcessor, SpiderTemplate spiderTemplate) {
        super(pageProcessor);
        this.spiderTemplate = spiderTemplate;
    }

    @Override
    protected void onSuccess(Request request) {
        super.onSuccess(request);

    }

    @Override
    protected void onError(Request request) {
        super.onError(request);
    }

    @Override
    public void close() {
        super.close();
    }
}
