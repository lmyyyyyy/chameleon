package cn.code.chameleon.spider.enums;

import cn.code.chameleon.scheduler.component.BloomFilterDuplicateRemover;
import cn.code.chameleon.scheduler.component.HashSetDuplicateRemover;

/**
 * URL去重枚举
 *
 * @author liumingyu
 * @create 2018-05-12 下午4:05
 */
public enum DuplicateRemoverEnum {

    HASH_SET(1, "哈希去重", new HashSetDuplicateRemover()),
    BLOOM_FILTER(2, "布隆过滤器去重", new BloomFilterDuplicateRemover(100000000)),
    REDIS_SET(3, "Redis哈希去重", null),
    REDIS_BLOOM_FILTER(4, "Redis布隆过滤器去重", null);

    private Integer code;

    private String desc;

    private Object object;

    DuplicateRemoverEnum(Integer code, String desc, Object object) {
        this.code = code;
        this.desc = desc;
        this.object = object;
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
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
