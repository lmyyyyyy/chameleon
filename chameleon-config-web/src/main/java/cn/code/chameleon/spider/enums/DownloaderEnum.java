package cn.code.chameleon.spider.enums;

/**
 * 下载器枚举
 *
 * @author liumingyu
 * @create 2018-05-12 下午4:05
 */
public enum DownloaderEnum {

    HTTP_CLIENT(1, "HttpClient下载器"),
    PHANTOM_JS(2, "PhantomJS下载器"),
    SELENIUM(3, "Selenium下载器");

    private Integer code;

    private String desc;

    DownloaderEnum(Integer code, String desc) {
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
    public static DownloaderEnum getDownloaderEnum(Integer code) {

        if (code == null) {
            return null;
        }
        DownloaderEnum returnEnum = null;
        for (DownloaderEnum downloaderEnum : DownloaderEnum.values()) {
            if (code.equals(downloaderEnum.getCode())) {
                returnEnum = downloaderEnum;
                break;
            }
        }
        return returnEnum;
    }
}
