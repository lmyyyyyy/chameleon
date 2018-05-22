package cn.code.chameleon.spider.enums;

/**
 * 格式类型枚举
 *
 * @author liumingyu
 * @create 2018-05-12 下午4:05
 */
public enum PatternTypeEnum {

    REGEX(1, "正则表达式"),
    XPATH(2, "XPath表达式"),
    CSS(3, "CSS选择器"),
    JSON_PATH(4, "JsonPath选择器");

    private Integer code;

    private String desc;

    PatternTypeEnum(Integer code, String desc) {
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
    public static PatternTypeEnum getPatternTypeEnum(Integer code) {

        if (code == null) {
            return null;
        }
        PatternTypeEnum returnEnum = null;
        for (PatternTypeEnum patternTypeEnum : PatternTypeEnum.values()) {
            if (code.equals(patternTypeEnum.getCode())) {
                returnEnum = patternTypeEnum;
                break;
            }
        }
        return returnEnum;
    }
}
