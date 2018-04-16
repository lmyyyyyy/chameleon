package cn.code.chameleon.carrier;

import cn.code.chameleon.selector.Html;
import cn.code.chameleon.selector.Json;
import cn.code.chameleon.selector.Selectable;
import cn.code.chameleon.utils.HttpConstant;
import cn.code.chameleon.utils.UrlUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-09 下午3:39
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 3842932539140632584L;

    private Request request;

    private Results results = new Results();

    private Html html;

    private Json json;

    private int statusCode = HttpConstant.StatusCode.CODE_200;

    private Map<String, List<String>> headers;

    private boolean downloadSuccess = true;

    private byte[] bytes;

    private List<Request> targetRequests = new ArrayList<>();

    private String charset;

    private String rawText;

    private Selectable url;

    public Page() {
    }

    public static Page fail() {
        Page page = new Page();
        page.setDownloadSuccess(false);
        return page;
    }

    public Page setJump(boolean isJump) {
        results.setJump(isJump);
        return this;
    }

    public Page putField(String key, Object field) {
        results.put(key, field);
        return this;
    }

    public Request getRequest() {
        return request;
    }

    public Page setRequest(Request request) {
        this.request = request;
        this.results.setRequest(request);
        return this;
    }

    public Results getResults() {
        return results;
    }

    public Page setResults(Results results) {
        this.results = results;
        return this;
    }

    public Html getHtml() {
        if (html == null) {
            html = new Html(rawText, request.getUrl());
        }
        return html;
    }

    public Page setHtml(Html html) {
        this.html = html;
        return this;
    }

    public Json getJson() {
        if (json == null) {
            json = new Json(rawText);
        }
        return json;
    }

    public Page setJson(Json json) {
        this.json = json;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Page setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public Page setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
        return this;
    }

    public boolean isDownloadSuccess() {
        return downloadSuccess;
    }

    public Page setDownloadSuccess(boolean downloadSuccess) {
        this.downloadSuccess = downloadSuccess;
        return this;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Page setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }

    public void addTargetRequests(List<String> requests) {
        for (String s : requests) {
            if (StringUtils.isBlank(s) || s.equals("#") || s.startsWith("javascript:")) {
                continue;
            }
            s = UrlUtils.canonicalizeUrl(s, url.toString());
            targetRequests.add(new Request(s));
        }
    }

    public void addTargetRequests(List<String> requests, long priority) {
        for (String s : requests) {
            if (StringUtils.isBlank(s) || s.equals("#") || s.startsWith("javascript:")) {
                continue;
            }
            s = UrlUtils.canonicalizeUrl(s, url.toString());
            targetRequests.add(new Request(s).setPriority(priority));
        }
    }

    public void addTargetRequest(String requestStr) {
        if (StringUtils.isBlank(requestStr) || requestStr.equals("#")) {
            return;
        }
        requestStr = UrlUtils.canonicalizeUrl(requestStr, url.toString());
        targetRequests.add(new Request(requestStr));
    }

    public void addTargetRequest(Request request) {
        targetRequests.add(request);
    }

    public List<Request> getTargetRequests() {
        return targetRequests;
    }

    public Page setTargetRequests(List<Request> targetRequests) {
        this.targetRequests = targetRequests;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public Page setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public String getRawText() {
        return rawText;
    }

    public Page setRawText(String rawText) {
        this.rawText = rawText;
        return this;
    }

    public Selectable getUrl() {
        return url;
    }


    public Page setUrl(Selectable url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return "Page{" +
                "request=" + request +
                ", results=" + results +
                ", html=" + html +
                ", json=" + json +
                ", statusCode=" + statusCode +
                ", headers=" + headers +
                ", downloadSuccess=" + downloadSuccess +
                ", bytes=" + Arrays.toString(bytes) +
                ", targetRequests=" + targetRequests +
                ", charset='" + charset + '\'' +
                ", rawText='" + rawText + '\'' +
                ", url=" + url +
                '}';
    }

}
