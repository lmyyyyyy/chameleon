package cn.code.chameleon.downloader;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.proxy.ProxyProvider;
import cn.code.chameleon.selector.Html;
import cn.code.chameleon.utils.HttpConstant;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-09 下午5:44
 */
public abstract class AbstractDownloader implements Downloader {

    public Html download(String url) {
        return download(url, HttpConstant.Charset.DEFAULT_CHARSET);
    }

    public Html download(String url, String charset) {
        Page page = download(new Request(url), Site.init().setCharset(charset).buildTask());
        return page.getHtml();
    }

    protected void onSuccess(Request request) {

    }

    protected void onError(Request request) {

    }
}
