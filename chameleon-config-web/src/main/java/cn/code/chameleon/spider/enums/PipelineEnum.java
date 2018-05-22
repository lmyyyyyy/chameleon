package cn.code.chameleon.spider.enums;

/**
 * 管道枚举
 *
 * @author liumingyu
 * @create 2018-05-12 下午4:05
 */
public enum PipelineEnum {

    CONSOLE_PIPELINE(1, "命令行管道"),
    FILE_PIPELINE(2, "文件管道"),
    REDIS_PIPELINE(3, "Redis管道"),
    ES_PIPELINE(4, "Elasticsearch管道");

    private Integer code;

    private String desc;

    PipelineEnum(Integer code, String desc) {
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
    public static PipelineEnum getPipelineEnum(Integer code) {

        if (code == null) {
            return null;
        }
        PipelineEnum returnEnum = null;
        for (PipelineEnum pipelineEnum : PipelineEnum.values()) {
            if (code.equals(pipelineEnum.getCode())) {
                returnEnum = pipelineEnum;
                break;
            }
        }
        return returnEnum;
    }
}
