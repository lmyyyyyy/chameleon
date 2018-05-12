package cn.code.chameleon.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liumingyu
 * @create 2018-05-07 下午5:35
 */
@RestController
@RequestMapping("/api/task")
@Api(value = "爬虫任务模块", tags = "爬虫任务模块")
public class ChameleonTaskController {
}
