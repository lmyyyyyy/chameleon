package cn.code.chameleon.downloader;

import cn.code.chameleon.carrier.HttpRequestBody;
import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Site;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.proxy.Proxy;
import cn.code.chameleon.proxy.SimpleProxyProvider;
import cn.code.chameleon.selector.Html;
import cn.code.chameleon.utils.CharsetUtils;
import cn.code.chameleon.utils.HttpConstant;
import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.Runnable;
import com.github.dreamhead.moco.Runner;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static com.github.dreamhead.moco.Moco.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author liumingyu
 * @create 2018-04-16 下午5:51
 */
public class HttpClientDownloaderTest {

    private HttpClientDownloader downloader = new HttpClientDownloader();

    @Test
    public void test_download_ssl() throws Exception {
        Task task = Site.init().setCycleRetryTimes(3).buildTask();
        Request request = new Request("https://juejin.im/");
        Page page = downloader.download(request, task);
        System.out.println("is success : " + page.isDownloadSuccess());
    }

    @Test
    public void test_download() throws Exception {
        Html html = downloader.download("https://www.baidu.com/");
        System.out.println(html.getFirstSourceText());
        System.out.println(html.getDocument());
        System.out.println(html.getElements());
        //html = downloader.download("http://www.oschina.net/>");
        //System.out.println(html.getFirstSourceText());
    }

    @Test
    public void test_download_failed() throws Exception {
        Task task = Site.init().setDomain("localhost").setCycleRetryTimes(3).buildTask();
        Request request = new Request("http://localhost:43436/500");
        Page page = downloader.download(request, task);
        System.out.println("is success : " + page.isDownloadSuccess());
    }

    @Test
    public void test_set_cookie() throws Exception {
        HttpServer httpServer = httpServer(13423);
        httpServer.get(eq(cookie("cookie"), "cookie-chameleon")).response("ok");
        Runner.running(httpServer, ()-> {
            HttpClientDownloader downloader = new HttpClientDownloader();
            Request request = new Request();
            request.setUrl("http://127.0.0.1:13423");
            request.addCookie("cookie", "cookie-chameleon");
            Page page = downloader.download(request, Site.init().buildTask());
            System.out.println("page raw text: " + page.getRawText());
        });
    }

    @Test
    public void test_set_disable_cookie() throws Exception {
        HttpServer httpServer = httpServer(13423);
        httpServer.get(not(eq(cookie("cookie"), "cookie-chameleon"))).response("ok");
        Runner.running(httpServer, ()-> {
            HttpClientDownloader downloader = new HttpClientDownloader();
            Request request = new Request();
            request.setUrl("http://127.0.0.1:13423");
            request.addCookie("cookie", "cookie-chameleon");
            Page page = downloader.download(request, Site.init().setDisableCookies(true).buildTask());
            System.out.println("page raw text: " + page.getRawText());
        });
    }

    @Test
    public void test_set_Headers() throws Exception {
        HttpServer httpServer = httpServer(13423);
        httpServer.get(eq(header("header"), "header-chameleon")).response("ok");
        Runner.running(httpServer, ()-> {
            HttpClientDownloader downloader = new HttpClientDownloader();
            Request request = new Request();
            request.setUrl("http://127.0.0.1:13423");
            request.addHeader("header", "header-chameleon");
            Page page = downloader.download(request, Site.init().buildTask());
            System.out.println("page raw text: " + page.getRawText());
        });
    }

    @Test
    public void test_set_site_Headers() throws Exception {
        HttpServer httpServer = httpServer(13423);
        httpServer.get(eq(header("header"), "header-chameleon")).response("ok");
        Runner.running(httpServer, ()-> {
            HttpClientDownloader downloader = new HttpClientDownloader();
            Request request = new Request();
            request.setUrl("http://127.0.0.1:13423");
            Page page = downloader.download(request, Site.init().addHeader("header","header-chameleon").buildTask());
            System.out.println("page raw text: " + page.getRawText());
        });
    }

    @Test
    public void test_set_site_Cookies() throws Exception {
        HttpServer httpServer = httpServer(13423);
        httpServer.get(eq(cookie("header"), "header-chameleon")).response("ok");
        Runner.running(httpServer, ()-> {
            HttpClientDownloader downloader = new HttpClientDownloader();
            Request request = new Request();
            request.setUrl("http://127.0.0.1:13423");
            Page page = downloader.download(request, Site.init().addCookie("127.0.0.1", "header","header-chameleon").buildTask());
            System.out.println("page raw text: " + page.getRawText());
        });
    }

    @Test
    public void test_download_when_task_is_null() throws Exception {
        HttpServer httpServer = httpServer(13423);
        httpServer.response("ok");
        Runner.running(httpServer, ()-> {
            HttpClientDownloader downloader = new HttpClientDownloader();
            Request request = new Request();
            request.setUrl("http://127.0.0.1:13423");
            Page page = downloader.download(request, Site.init().buildTask());
            System.out.println("page raw text: " + page.getRawText());
        });
    }

    @Test
    public void test_download_auth_by_proxy_provider() throws Exception {
        HttpServer httpServer = httpServer(13423);
        httpServer.get(eq(header("Proxy-Authorization"), "Basic dXNlcm5hbWU6cGFzc3dvcmQ=")).response("ok");
        Runner.running(httpServer, ()-> {
            HttpClientDownloader downloader = new HttpClientDownloader();
            downloader.setProxyProvider(SimpleProxyProvider.store(new Proxy("127.0.0.1", 13423, "username", "password")));
            Request request = new Request();
            request.setUrl("http://www.baidu.com");
            Page page = downloader.download(request, Site.init().buildTask());
            System.out.println("page raw text: " + page.getRawText());
        });
    }

    @Test
    public void test_download_binary_content() throws Exception {
        HttpServer server = httpServer(13423);
        server.response("binary");
        Runner.running(server, ()-> {
                 HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
                Request request = new Request();
                request.setBinaryContent(true);
                request.setUrl("http://127.0.0.1:13423/");
                Page page = httpClientDownloader.download(request, Site.init().buildTask());
                assertThat(page.getRawText()).isNull();
                assertThat(page.getBytes()).isEqualTo("binary".getBytes());
            }
        );
    }

    @Test
    public void test_download_set_charset() throws Exception {
        HttpServer server = httpServer(13423);
        server.response(header("Content-Type","text/html; charset=utf-8")).response("hello world!");
        Runner.running(server, () -> {
                final HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
                Request request = new Request();
                request.setUrl("http://127.0.0.1:13423/");
                Page page = httpClientDownloader.download(request, Site.init().buildTask());
                assertThat(page.getCharset()).isEqualTo("utf-8");
            }
        );
    }

    @Test
    public void test_download_set_request_charset() throws Exception {
        HttpServer server = httpServer(13423);
        server.response("hello world!");
        Runner.running(server, () -> {
                final HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
                Request request = new Request();
                request.setCharset("utf-8");
                request.setUrl("http://127.0.0.1:13423/");
                Page page = httpClientDownloader.download(request, Site.init().setCharset("gbk").buildTask());
                assertThat(page.getCharset()).isEqualTo("utf-8");
            }
        );
    }

    @Test
    public void testGetHtmlCharset() throws Exception {
        HttpServer server = httpServer(13423);
        server.get(by(uri("/header"))).response(header("Content-Type", "text/html; charset=gbk"));
        server.get(by(uri("/meta4"))).response(with(text("<html>\n" +
                "  <head>\n" +
                "    <meta charset='gbk'/>\n" +
                "  </head>\n" +
                "  <body></body>\n" +
                "</html>")),header("Content-Type","text/html; charset=gbk"));
        server.get(by(uri("/meta5"))).response(with(text("<html>\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\" />\n" +
                "  </head>\n" +
                "  <body></body>\n" +
                "</html>")),header("Content-Type","text/html"));
        Runner.running(server, new Runnable() {
            @Override
            public void run() {
                String charset = getCharsetByUrl("http://127.0.0.1:13423/header");
                assertEquals(charset, "gbk");
                charset = getCharsetByUrl("http://127.0.0.1:13423/meta4");
                assertEquals(charset, "gbk");
                charset = getCharsetByUrl("http://127.0.0.1:13423/meta5");
                assertEquals(charset, "gbk");
            }

            private String getCharsetByUrl(String url) {
                Site site = Site.init();
                CloseableHttpClient httpClient = new HttpClientGenerator().getClient(site);
                // encoding in http header Content-Type
                Request requestGBK = new Request(url);
                CloseableHttpResponse httpResponse = null;
                try {
                    httpResponse = httpClient.execute(new HttpUriRequestConverter().convert(requestGBK, site, null).getHttpUriRequest());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String charset = null;
                try {
                    byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
                    charset = CharsetUtils.detectCharset(httpResponse.getEntity().getContentType().getValue(), contentBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return charset;
            }
        });
    }

    @Test
    public void test_selectRequestMethod() throws Exception {
        final int port = 13423;
        HttpServer server = httpServer(port);
        server.get(eq(query("q"), "chameleon")).response("get");
        server.post(eq(form("q"), "chameleon")).response("post");
        server.put(eq(form("q"), "chameleon")).response("put");
        server.delete(eq(query("q"), "chameleon")).response("delete");
        server.request(and(by(method("HEAD")),eq(query("q"), "chameleon"))).response(header("method","head"));
        server.request(and(by(method("TRACE")),eq(query("q"), "chameleon"))).response("trace");
        final HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();
        final Site site = Site.init();
        Runner.running(server, () -> {
                Request request = new Request();
                request.setUrl("http://127.0.0.1:" + port + "/search?q=chameleon");
                request.setMethod(HttpConstant.Method.GET);
                Map<String,Object> params = new HashedMap();
                params.put("q","chameleon");
                HttpUriRequest httpUriRequest = httpUriRequestConverter.convert(request,site,null).getHttpUriRequest();
                assertThat(EntityUtils.toString(HttpClients.custom().build().execute(httpUriRequest).getEntity())).isEqualTo("get");
                request.setMethod(HttpConstant.Method.DELETE);
                httpUriRequest = httpUriRequestConverter.convert(request, site, null).getHttpUriRequest();
                assertThat(EntityUtils.toString(HttpClients.custom().build().execute(httpUriRequest).getEntity())).isEqualTo("delete");
                request.setMethod(HttpConstant.Method.HEAD);
                httpUriRequest = httpUriRequestConverter.convert(request, site, null).getHttpUriRequest();
                assertThat(HttpClients.custom().build().execute(httpUriRequest).getFirstHeader("method").getValue()).isEqualTo("head");
                request.setMethod(HttpConstant.Method.TRACE);
                httpUriRequest = httpUriRequestConverter.convert(request, site, null).getHttpUriRequest();
                assertThat(EntityUtils.toString(HttpClients.custom().build().execute(httpUriRequest).getEntity())).isEqualTo("trace");
                request.setUrl("http://127.0.0.1:" + port + "/search");
                request.setMethod(HttpConstant.Method.POST);
                request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
                httpUriRequest = httpUriRequestConverter.convert(request, site, null).getHttpUriRequest();
                assertThat(EntityUtils.toString(HttpClients.custom().build().execute(httpUriRequest).getEntity())).isEqualTo("post");
                request.setMethod(HttpConstant.Method.PUT);
                httpUriRequest = httpUriRequestConverter.convert(request, site, null).getHttpUriRequest();
                assertThat(EntityUtils.toString(HttpClients.custom().build().execute(httpUriRequest).getEntity())).isEqualTo("put");
            }
        );
    }
}
