package cn.code.chameleon.utils;

/**
 * @author liumingyu
 * @create 2018-06-02 上午11:00
 */
public class PageUtil {

    public static String getPage(String url, Integer page, Integer size, Long pageCount) {
        String urlNew = url.replace("{size}", size + "");

        String first = urlNew.replace("{page}", 1 + "");
        String prev = urlNew.replace("{page}", (page - 1) + "");
        String next = urlNew.replace("{page}", (page + 1) + "");
        String last = urlNew.replace("{page}", pageCount + "");
        StringBuffer html = new StringBuffer();
        html.append("<li class=\"footable-page-arrow" + (page <= 1 ? " disabled" : "") + "\"><a href=\"" + (page <= 1 ? "#" : first) + "\">«</a></li>");
        html.append("<li class=\"footable-page-arrow" + (page <= 1 ? " disabled" : "") + "\"><a href=\"" + (page <= 1 ? "#" : prev) + "\">‹</a></li>");
        for (int i = 0; i < pageCount.intValue(); i++) {
            String urlItem = urlNew.replace("{page}", (i + 1) + "");
            html.append("<li class=\"footable-page" + (((i + 1) == page) ? " active" : "") + "\"><a href=\"" + urlItem + "\">" + (i + 1) + "</a></li>");
        }
        html.append("<li class=\"footable-page-arrow" + (page == pageCount.intValue() ? " disabled" : "") + "\"><a href=\"" + (page == pageCount.intValue() ? "#" : next) + "\">›</a></li>");
        html.append("<li class=\"footable-page-arrow" + (page == pageCount.intValue() ? " disabled" : "") + "\"><a href=\"" + (page == pageCount.intValue() ? "#" : last) + "\">»</a></li>");

        return html.toString().replaceAll("null", "");
    }
}
