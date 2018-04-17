package cn.code.chameleon.selector;

import org.junit.Test;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-17 下午4:22
 */
public class SelectorTest {

    private String html = "<div class=\"body clearfix\">\n" +
            "                            <ul class=\"tags\">\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=Java\">Java</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=爬虫\">爬虫</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=Spring\">Spring</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=Mabtis\">Mabtis</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=Hibernate\">Hibernate</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=Struts2\">Struts2</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=Exception\">Exception</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=Offer\">Offer</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=Ajax\">Ajax</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=前端\">前端</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=算法\">算法</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=设计模式\">设计模式</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=博客更新\">博客更新</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=Git\">Git</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=框架\">框架</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=SSO\">SSO</a></li>\n" +
            "                            \n" +
            "                            \t<li><a href=\"/home?blogType=项目\">项目</a></li>\n" +
            "                            \n" +
            "                            </ul>\n" +
            "                        </div>";

    @Test
    public void test_selector() throws Exception {
        Html selectable = new Html(html, "http://www.lblogg.cn/");
        List<String> withoutList = selectable.links().all();
        System.out.println(withoutList);
        Selectable xpath = selectable.xpath("//li");
        List<String> linksChainList = xpath.links().all();
        System.out.println(linksChainList);
    }

    @Test
    public void test_nodes() throws Exception {
        Html selectable = new Html(html);
        List<Selectable> links = selectable.xpath("//a").nodes();
        System.out.println(links.get(0).links().get());
        Json json = new Json(html);
        System.out.println(json.getFirstSourceText());
    }
}
