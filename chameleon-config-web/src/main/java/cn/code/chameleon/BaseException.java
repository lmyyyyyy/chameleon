package cn.code.chameleon;

import cn.code.chameleon.common.UnifiedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liumingyu
 * @create 2018-05-05 下午12:30
 */
@ControllerAdvice
@Slf4j
public class BaseException {

    @ExceptionHandler(value = Exception.class)
    public UnifiedResponse defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        return new UnifiedResponse();
    }
}
