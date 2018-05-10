package cn.code.chameleon.registry;

import cn.code.chameleon.service.RedisClient;
import cn.code.chameleon.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liumingyu
 * @create 2018-05-08 下午9:03
 */
@Component
public class ConfigRegistry implements InitializingBean, BeanFactoryAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigRegistry.class);

    private static final String LOG_PREFIX = "[服务注册中心] ";

    private BeanFactory beanFactory;

    @Autowired
    private RedisClient redisClient;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("{} 启动, 开始检查注册所有配置项", LOG_PREFIX);
        String seconds = redisClient.get(Constants.TIME_INTERVAL);
        if (seconds == null || "".equals(seconds)) {
            redisClient.set(Constants.TIME_INTERVAL, String.valueOf(Constants.TIME_INTERVAL_SECONDS));
        }

        String print_log_switch = redisClient.get(Constants.METHOD_LOG_PRINT_SWITCH_KEY);
        if (print_log_switch == null || "".equals(print_log_switch)) {
            redisClient.set(Constants.METHOD_LOG_PRINT_SWITCH_KEY, String.valueOf(Constants.METHOD_LOG_PRINT_SWITCH));
        }

        String record_sql_switch = redisClient.get(Constants.RECORD_SQL_SWITCH_KEY);
        if (record_sql_switch == null || "".equals(record_sql_switch)) {
            redisClient.set(Constants.RECORD_SQL_SWITCH_KEY, String.valueOf(Constants.RECORD_SQL_SWITCH));
        }

        String log_to_db_switch = redisClient.get(Constants.LOG_TO_DB_KEY);
        if (log_to_db_switch == null || "".equals(log_to_db_switch)) {
            redisClient.set(Constants.LOG_TO_DB_KEY, String.valueOf(Constants.LOG_TO_DB));
        }

        String mapper_to_db_switch = redisClient.get(Constants.MAPPER_LOG_SINGLE_TO_DB_KEY);
        if (mapper_to_db_switch == null || "".equals(mapper_to_db_switch)) {
            redisClient.set(Constants.MAPPER_LOG_SINGLE_TO_DB_KEY, String.valueOf(Constants.MAPPER_LOG_SINGLE_TO_DB));
        }
        LOGGER.info("{} 检查注册所有配置项成功!", LOG_PREFIX);
    }
}
