package cn.code.chameleon.downloader;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.proxy.Proxy;
import cn.code.chameleon.proxy.ProxyProvider;
import cn.code.chameleon.selector.PlainText;
import cn.code.chameleon.utils.CharsetUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-12 上午11:30
 */
public class HttpClientDownloader extends AbstractDownloader {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final Map<String, CloseableHttpClient> httpClients = new HashMap<>();

    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

    private HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();

    private ProxyProvider proxyProvider;

    private boolean responseHeader = true;

    public void setProxyProvider(ProxyProvider proxyProvider) {
        this.proxyProvider = proxyProvider;
    }

    public void setHttpUriRequestConverter(HttpUriRequestConverter httpUriRequestConverter) {
        this.httpUriRequestConverter = httpUriRequestConverter;
    }

    @Override
    public Page download(Request request, Task task) {
        if (task == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = getHttpClient(task.getSite());
        Proxy proxy = proxyProvider != null ? proxyProvider.getProxy(task) : null;
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(request, task.getSite(), proxy);
        Page page = Page.fail();
        try {
            httpResponse = httpClient.execute(requestContext.getHttpUriRequest(), requestContext.getHttpClientContext());
            page = handleResponse(request, request.getCharset() != null ? request.getCharset() : task.getSite().getCharset(), httpResponse, task);
            onSuccess(request);
            LOGGER.info("{} download page {} success", Thread.currentThread().getName(), request.getUrl());
            return page;
        } catch (IOException e) {
            LOGGER.warn("{} download page {} error", Thread.currentThread().getName(), request.getUrl(), e);
            onError(request);
            return page;
        } finally {
            if (httpResponse != null) {
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
        }
    }

    @Override
    public void setThread(int threadNum) {
        httpClientGenerator.setPoolSize(threadNum);
    }

    private CloseableHttpClient getHttpClient(Site site) {
        if (site == null) {
            return httpClientGenerator.getClient(null);
        }
        String domain = site.getDomain();
        CloseableHttpClient httpClient = httpClients.get(domain);
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(site);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }

    protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task) throws IOException {
        byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
        String contentType = httpResponse.getEntity().getContentType() == null ? "" : httpResponse.getEntity().getContentType().getValue();
        Page page = new Page();
        page.setBytes(bytes);
        if (!request.isBinaryContent()) {
            if (charset == null) {
                charset = getHtmlCharset(contentType, bytes);
            }
            page.setCharset(charset);
            page.setRawText(new String(bytes, charset));
        }
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        page.setDownloadSuccess(true);
        if (responseHeader) {
            page.setHeaders(convertHeaders(httpResponse.getAllHeaders()));
        }
        return page;
    }

    private String getHtmlCharset(String contentType, byte[] contentBytes) throws IOException {
        String charset = CharsetUtils.detectCharset(contentType, contentBytes);
        if (charset == null) {
            charset = Charset.defaultCharset().name();
            LOGGER.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()", Charset.defaultCharset());
        }
        return charset;
    }

    private static Map<String, List<String>> convertHeaders(Header[] headers) {
        Map<String, List<String>> results = new HashMap<>();
        for (Header header : headers) {
            List<String> list = results.get(header.getName());
            if (list == null) {
                list = new ArrayList<>();
                results.put(header.getName(), list);
            }
            list.add(header.getValue());
        }
        return results;
    }

}
