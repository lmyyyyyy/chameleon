package cn.code.chameleon.monitor.actions.commands.impl.service_command;


import cn.code.chameleon.monitor.actions.commands.AbstractCommand;
import cn.code.chameleon.monitor.actions.context.ActionContext;
import cn.code.chameleon.monitor.actions.exception.EntityIsNullException;
import cn.code.chameleon.monitor.annotation.Command;
import cn.code.chameleon.monitor.aspect.ServiceAspect;
import cn.code.chameleon.monitor.pojo.ServiceLog;
import cn.code.chameleon.monitor.pojo.ServiceLogWithBLOBs;
import cn.code.chameleon.monitor.service.RunMethodLogService;
import cn.code.chameleon.monitor.util.LogUtil;
import cn.code.chameleon.service.RedisClient;
import cn.code.chameleon.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.SERVICE_INVOKER;
import static cn.code.chameleon.monitor.annotation.Command.ORDER_LAST;

/**
 * service方法日志存入数据库
 */
@Component("ServiceLogToDBCommand")
@Command(order = ORDER_LAST, invoker = SERVICE_INVOKER)
public class ServiceLogToDBCommand extends AbstractCommand<ServiceLogWithBLOBs> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);
    @Autowired
    private RunMethodLogService runMethodLogService;

    @Autowired
    private RedisClient redisClient;

    @Override
    public void execute() {
        LOGGER.info("【ServiceLogToDBCommand执行命令】Service日志入库");
        ActionContext<ServiceLogWithBLOBs> actionContext = getActionContext();
        if (actionContext.getEntity() == null) {
            throw new EntityIsNullException("RunServiceLogWithBLOBs对象为空");
        }

        Boolean flag = Constants.LOG_TO_DB;

        String temp = redisClient.get(Constants.LOG_TO_DB_KEY);
        if (temp != null && !"".equals(temp)) {
            if (!flag.toString().equals(temp)) {
                flag = !flag;
            }
        }

        //更新数据库
        if (flag && shouldOutput(actionContext.getEntity())) {
            //批量插入serviceLog记录
            runMethodLogService.batchSaveServiceLog(LogUtil.serviceLogList.get());

            flag = Constants.MAPPER_LOG_SINGLE_TO_DB;
            temp = redisClient.get(Constants.MAPPER_LOG_SINGLE_TO_DB_KEY);
            if (temp != null && !"".equals(temp)) {
                if (!flag.toString().equals(temp)) {
                    flag = !flag;
                }
            }

            if (flag) {
                runMethodLogService.batchSaveMapperLog(LogUtil.getRunMapperLogList());
            }
        }
        //清空threadlocal
        if (shouldClear(actionContext.getEntity())) {
            LogUtil.clear();
        }
    }

    /**
     * 判断当前runServiceLog是否需要输出
     *
     * @param runServiceLog
     * @return
     */
    private boolean shouldOutput(ServiceLog runServiceLog) {
        return runServiceLog.isTop();
    }

    /**
     * 判断是否需要清除threadlocal中的runServiceLog相关记录
     *
     * @param runServiceLog
     * @return
     */
    private boolean shouldClear(ServiceLogWithBLOBs runServiceLog) {
        return runServiceLog.isTop() || Constants.IS_CLEAR_SERVICE_LOG_LIST;
    }

}
