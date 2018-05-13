package cn.code.chameleon.spider.enums;

/**
 * URL去重枚举
 *
 * @author liumingyu
 * @create 2018-05-12 下午4:05
 */
public enum DuplicateRemoverEnum {

    HASH_SET(1, "哈希去重"),
    BLOOM_FILTER(2, "布隆过滤器去重"),
    REDIS_SET(3, "Redis哈希去重"),
    REDIS_BLOOM_FILTER(4, "Redis布隆过滤器去重");

    private Integer code;

    private String desc;

    DuplicateRemoverEnum(Integer code, String desc) {
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
    public static DuplicateRemoverEnum getDuplicateRemoverEnum(Integer code) {

        if (code == null) {
            return null;
        }
        DuplicateRemoverEnum returnEnum = null;
        for (DuplicateRemoverEnum duplicateRemoverEnum : DuplicateRemoverEnum.values()) {
            if (code.equals(duplicateRemoverEnum.getCode())) {
                returnEnum = duplicateRemoverEnum;
                break;
            }
        }
        return returnEnum;
    }
}
