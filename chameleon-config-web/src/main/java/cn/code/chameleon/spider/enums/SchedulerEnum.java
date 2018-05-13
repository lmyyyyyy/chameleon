package cn.code.chameleon.spider.enums;

/**
 * 调度器枚举
 *
 * @author liumingyu
 * @create 2018-05-12 下午4:05
 */
public enum SchedulerEnum {
    QUEUE(1, "队列调度器"),
    PRIORITY_QUEUE(2, "优先级队列调度器"),
    REDIS_QUEUE(3, "Redis调度器"),
    REDIS_PRIORITY_QUEUE(4, "Redis优先级调度器"),
    FILE_CACHE_QUEUE(5, "文件缓存调度器");

    private Integer code;

    private String desc;

    SchedulerEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    /**
     * 根据code获取desc
     *
     * @param code
     * @return
     */
    public static SchedulerEnum getSchedulerEnum(Integer code) {

        if (code == null) {
            return null;
        }
        SchedulerEnum returnEnum = null;
        for (SchedulerEnum schedulerEnum : SchedulerEnum.values()) {
            if (code.equals(schedulerEnum.getCode())) {
                returnEnum = schedulerEnum;
                break;
            }
        }
        return returnEnum;
    }
}
