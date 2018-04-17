package cn.code.chameleon.selector;

import org.jsoup.Jsoup;
import org.junit.Test;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-17 下午12:28
 */
public class LinksSelectorTest {

    private LinksSelector linksSelector = new LinksSelector();

    private String html = "<div class=\"aside-widget\">\n" +
            "                        <header>\n" +
            "                            <h3>Ji Qing Links</h3>\n" +
            "                        </header>\n" +
            "                        <div class=\"body\">\n" +
            "                            <ul class=\"clean-list\">\n" +
            "                            \n" +
            "                            \t<li><a href=\"http://isanson.me\">http://isanson.me</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"https://www.nowcoder.com/profile/982925\">https://www.nowcoder.com/profile/982925</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"https://juejin.im/\">https://juejin.im/</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"https://www.cnblogs.com/\">https://www.cnblogs.com/</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"http://www.csdn.net/\">http://www.csdn.net/</a></li>\n" +
            "                            \n" +
            "                            </ul>\n" +
            "                        </div>\n" +
            "                    </div>\n";

    @Test
    public void test_link() throws Exception {
        List<String> links = linksSelector.selectList(html);
        System.out.println(links);

        html = "<div class=\"body\">\n" +
                "                            <ul class=\"clean-list\">\n" +
                "                            \n" +
                "                                <li><a href=\"/single/124\">大名鼎鼎的二叉树神级遍历Morris</a></li>\n" +
                "                             \n" +
                "                                <li><a href=\"/single/121\">Java判断两个链表是否相交，如果相交返回第一个节点</a></li>\n" +
                "                             \n" +
                "                                <li><a href=\"/single/118\">Java字符串转整型</a></li>\n" +
                "                             \n" +
                "                                <li><a href=\"/single/83\">单例模式的几种方法</a></li>\n" +
                "                             \n" +
                "                                <li><a href=\"http://www.lblogg.cn/single/117\">无序数组中第K大的数</a></li>\n" +
                "                             \n" +
                "                            </ul>\n";
        links = linksSelector.selectList(Jsoup.parse(html, "http://www.lblogg.cn/"));
        System.out.println(links);
    }
}
