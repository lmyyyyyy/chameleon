package cn.code.chameleon.action;

import cn.code.chameleon.common.UnifiedResponse;
import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.exception.ChameleonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author liumingyu
 * @create 2017-11-28 上午10:24
 */
@Controller
public class BaseController {
    private static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(Throwable.class)
    public UnifiedResponse HandleAllException(Throwable e) {
        if (e instanceof ChameleonException) {
            return new UnifiedResponse(((ChameleonException) e).getCode(), e.getMessage());
        } else {
            LOGGER.error("default error handler: " + e.getMessage());
            return new UnifiedResponse(ResultCodeEnum.FAILED.getCode(), ResultCodeEnum.FAILED.getMsg());
        }
    }
}