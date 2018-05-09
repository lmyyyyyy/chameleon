package cn.code.chameleon;

import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseException.class);

    @ExceptionHandler(value = Throwable.class)
    public UnifiedResponse defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        if (e instanceof ChameleonException) {
            return new UnifiedResponse(((ChameleonException) e).getCode(), e.getMessage());
        } else {
            LOGGER.error("default error handler: " + e.getMessage());
            return new UnifiedResponse(ResultCodeEnum.FAILED.getCode(), ResultCodeEnum.FAILED.getMsg());
        }
    }
}
