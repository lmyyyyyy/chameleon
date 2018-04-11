package cn.code.chameleon.utils;

/**
 * @author liumingyu
 * @create 2018-04-10 下午4:14
 */
public class HttpConstant {

    public static abstract class Method {

        public static final String GET = "GET";

        public static final String HEAD = "HEAD";

        public static final String POST = "POST";

        public static final String PUT = "PUT";

        public static final String DELETE = "DELETE";

        public static final String TRACE = "TRACE";

        public static final String CONNECT = "CONNECT";

    }

    public static abstract class StatusCode {

        public static final int CODE_200 = 200;

        public static final int CODE_403 = 403;

        public static final int CODE_404 = 404;

    }

    public static abstract class Header {

        public static final String REFERER = "Referer";

        public static final String USER_AGENT = "User-Agent";
    }
}
