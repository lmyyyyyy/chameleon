package cn.code.chameleon.monitor.actions.commands.impl.mapper_command;


import cn.code.chameleon.monitor.actions.commands.AbstractCommand;
import cn.code.chameleon.monitor.actions.context.ActionContext;
import cn.code.chameleon.monitor.actions.exception.EntityIsNullException;
import cn.code.chameleon.monitor.annotation.Command;
import cn.code.chameleon.monitor.aspect.MapperAspect;
import cn.code.chameleon.monitor.pojo.MapperLogWithBLOBs;
import cn.code.chameleon.service.RedisClient;
import cn.code.chameleon.utils.Constants;
import cn.code.chameleon.utils.JsonUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.code.chameleon.monitor.actions.constants.BeanNameConstant.MAPPER_INVOKER;
import static cn.code.chameleon.monitor.annotation.Command.ORDER_START;
import static cn.code.chameleon.monitor.util.MyBatisUtil.getSQL4MapperMethod;

/**
 * 记录执行的sql语句
 *
 * @author liumingyu
 * @create 2018-01-21 下午5:25
 */
@Component("RecordSQLCommand")
@Command(order = ORDER_START + 1, invoker = {MAPPER_INVOKER})
public class RecordSQLCommand extends AbstractCommand<MapperLogWithBLOBs> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapperAspect.class);

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private RedisClient redisClient;

    @Override
    public void execute() {
        LOGGER.info("【RecordSQLCommand执行命令】记录执行SQL语句");

        Boolean flag = Constants.RECORD_SQL_SWITCH;

        String temp = redisClient.get(Constants.RECORD_SQL_SWITCH_KEY);
        if (temp != null && !"".equals(temp)) {
            if (!flag.toString().equals(temp)) {
                flag = !flag;
            }
        }

        //记录执行的sql
        if (flag) {
            ActionContext<MapperLogWithBLOBs> actionContext = getActionContext();
            MapperLogWithBLOBs mapperLog = actionContext.getEntity();
            JoinPoint joinPoint = actionContext.getJoinPoint();
            if (mapperLog == null) {
                throw new EntityIsNullException("RunMapperLogWithBLOBs实体类不存在");
            }
            try {
                mapperLog.setSqlStatement(JsonUtils.toJsonStr(getSQL4MapperMethod(joinPoint, sqlSessionFactory)));
            } catch (Exception e) {
                LOGGER.error("[MethodLog]- {} parse sql statement error : {}", actionContext.getInvokerName(), e);
            }
        }
    }
}
