package cn.code.chameleon.interceptor;

import cn.code.chameleon.model.TaskManager;
import cn.code.chameleon.pojo.ChameleonTask;
import cn.code.chameleon.service.ChameleonTaskService;
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
    private ChameleonTaskService chameleonTaskService;

    @Autowired
    private TaskManager taskManager;

    @Override
    public void destroy() throws Exception {
        List<ChameleonTask> tasks = taskManager.getRunningTasks();
        System.out.println("服务器被关闭啦");
    }

    @Override
    public int getExitCode() {
        return 0;
    }
}
