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

        String sso_token_expire = redisClient.get(Constants.SSO_TOKEN_EXPIRE_KEY);
        if (sso_token_expire == null || "".equals(sso_token_expire)) {
            redisClient.set(Constants.SSO_TOKEN_EXPIRE_KEY, String.valueOf(Constants.SSO_TOKEN_EXPIRE));
        }
        LOGGER.info("{} 检查注册所有配置项成功!", LOG_PREFIX);
    }
}
