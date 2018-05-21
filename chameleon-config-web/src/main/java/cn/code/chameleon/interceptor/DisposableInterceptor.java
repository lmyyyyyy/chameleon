package cn.code.chameleon.interceptor;

import cn.code.chameleon.model.TaskStatisticsManager;
import cn.code.chameleon.pojo.ChameleonStatistics;
import cn.code.chameleon.service.ChameleonStatisticsService;
import cn.code.chameleon.service.SpiderService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-05-15 下午3:57
 */
@Component
public class DisposableInterceptor implements DisposableBean, ExitCodeGenerator {

    @Autowired
    private ChameleonStatisticsService chameleonStatisticsService;

    @Autowired
    private TaskStatisticsManager taskStatisticsManager;

    @Autowired
    private SpiderService spiderService;

    @Override
    public void destroy() throws Exception {
        //更新所有统计
        List<ChameleonStatistics> statisticsList = taskStatisticsManager.all();
        chameleonStatisticsService.updateStatistics(statisticsList, 0L);
        //关闭所有爬虫
        spiderService.stopAll(0L);
        System.out.println("服务器被关闭啦");
    }

    @Override
    public int getExitCode() {
        return 0;
    }
}
