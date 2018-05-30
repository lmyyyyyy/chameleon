package cn.code.chameleon.interceptor;

import cn.code.chameleon.service.RedisClient;
import cn.code.chameleon.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author liumingyu
 * @create 2018-05-10 下午3:14
 */
@Component
public class CoresFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoresFilter.class);

    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String ORIGIN = "Origin";
    public static final String OPTIONS = "OPTIONS";
    public static final String METHODS = "GET, POST, PUT, DELETE, OPTIONS";
    public static final String HEADERS = "X-Requested-With, Origin, Content-Type, Accept";
    public static final String MSG = "请求来源不合法";

    @Autowired
    private RedisClient redisClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String origin = request.getHeader(ORIGIN);
        if (origin == null || "".equals(origin)) {
            origin = "http://localhost:8080";
        }
        List<Object> origins = redisClient.lrange(Constants.FRONT_END_DOMAIN, 0, -1);
        if (null == origin || (!origin.contains("localhost") && !origins.contains(origin))) {
            LOGGER.warn("{} {}", MSG, origin);
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("origin: " + origin);
        response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        response.addHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString());
        response.addHeader(ACCESS_CONTROL_ALLOW_METHODS, METHODS);
        if (request.getHeader(ACCESS_CONTROL_REQUEST_METHOD) != null && OPTIONS.equals(request.getMethod())) {
            response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, HEADERS);
        }
        filterChain.doFilter(request, response);
    }
}
