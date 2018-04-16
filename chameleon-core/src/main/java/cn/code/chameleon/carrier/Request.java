package cn.code.chameleon.carrier;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liumingyu
 * @create 2018-04-09 下午3:39
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 8371099522850115521L;

    public static final String CYCLE_TRIED_TIMES = "cycle_tried_times";

    private String url;

    private String method;

    private Map<String, String> cookies = new HashMap<>();

    private Map<String, String> headers = new HashMap<>();

    private Map<String, Object> extras;

    private long priority;

    private boolean binaryContent = false;

    private String charset;

    private HttpRequestBody requestBody;

    public Request() {

    }

    public Request(String url) {
        this.url = url;
    }

    public Request setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Request setMethod(String method) {
        this.method = method;
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Request setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }

    public Request addCookie(String key, String value) {
        cookies.put(key, value);
        return this;
    }

    public Request addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Request setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Object getExtra(String key) {
        if (extras == null || extras.isEmpty()) {
            return null;
        }
        return extras.get(key);
    }

    public Request putExtra(String key, Object value) {
        if (extras == null) {
            this.extras = new HashMap<>();
        }
        extras.put(key, value);
        return this;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    public Request setExtras(Map<String, Object> extras) {
        this.extras = extras;
        return this;
    }

    public long getPriority() {
        return priority;
    }

    public Request setPriority(long priority) {
        this.priority = priority;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public Request setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public boolean isBinaryContent() {
        return binaryContent;
    }

    public Request setBinaryContent(boolean binaryContent) {
        this.binaryContent = binaryContent;
        return this;
    }

    public HttpRequestBody getRequestBody() {
        return requestBody;
    }

    public Request setRequestBody(HttpRequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (!url.equals(request.url)) return false;
        return method.equals(request.method);
    }

    @Override
    public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + method.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", cookies=" + cookies +
                ", headers=" + headers +
                ", extras=" + extras +
                ", priority=" + priority +
                ", binaryContent=" + binaryContent +
                ", charset='" + charset + '\'' +
                ", requestBody=" + requestBody +
                '}';
    }

}
