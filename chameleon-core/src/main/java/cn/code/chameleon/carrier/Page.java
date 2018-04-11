package cn.code.chameleon.carrier;

import cn.code.chameleon.utils.HttpConstant;

import java.io.Serializable;
import java.util.ArrayList;
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

    private int statusCode = HttpConstant.StatusCode.CODE_200;

    private Map<String, List<String>> headers;

    private boolean downloadSuccess = true;

    private byte[] bytes;

    private List<Request> targetRequests = new ArrayList<>();

    private String charset;

    private String rawText;

    public Page() {}

    public static Page fail() {
        Page page = new Page();
        return page;
    }
}
