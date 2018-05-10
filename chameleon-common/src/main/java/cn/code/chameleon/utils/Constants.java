package cn.code.chameleon.utils;

/**
 * @author liumingyu
 * @create 2018-05-08 下午9:08
 */
public class Constants {

    /**
     * 时间间隔key
     */
    public static final String TIME_INTERVAL = "TIME_INTERVAL";

    /**
     * 时间间隔数
     */
    public static final Long TIME_INTERVAL_SECONDS = 120L;

    /**
     * redis token key
     */
    public static final String USER_TOKEN_KEY = "USER_TOKEN";

    /**
     * token 过期时间
     */
    public static final Integer SSO_TOKEN_EXPIRE = 1800;

    /**
     * token cookie key
     */
    public static final String TOKEN_COOKIE = "CHAMELEON_TOKEN";

    /**
     * threadLocal副本key
     */
    public static final String USER_KEY = "CHAMELEON_USER";

    /**
     * 是否打印日志key
     */
    public static final String METHOD_LOG_PRINT_SWITCH_KEY = "METHOD_LOG_PRINT_SWITCH";

    /**
     * 是否打印日志
     */
    public static final Boolean METHOD_LOG_PRINT_SWITCH = true;

    /**
     * 是否记录sql key
     */
    public static final String RECORD_SQL_SWITCH_KEY = "RECORD_SQL_SWITCH";

    /**
     * 是否记录sql
     */
    public static final Boolean RECORD_SQL_SWITCH = true;

    /**
     * 日志是否入库key
     */
    public static final String LOG_TO_DB_KEY = "LOG_TO_DB";

    /**
     * 日志是否入库
     */
    public static final Boolean LOG_TO_DB = true;

    /**
     * mapper是否独立存储key
     */
    public static final String MAPPER_LOG_SINGLE_TO_DB_KEY = "MAPPER_LOG_TO_DB";

    /**
     * mapper是否独立存储
     */
    public static final Boolean MAPPER_LOG_SINGLE_TO_DB = true;

    /**
     * 是否清除service栈
     */
    public static final Boolean IS_CLEAR_SERVICE_LOG_LIST = true;

    /**
     * 是否递归记录一个线程中的所有方法
     */
    public static final Boolean SERVICE_LOG_RECURSION_SWITCH = true;

    /**
     * 前端域名集合
     */
    public static final String FRONT_END_DOMAIN = "FRONT_END_DOMAIN";

    /**
     * cookie种在此域名下
     */
    public static final String BASE_DOMAIN = "BASE_DOMAIN";

}
