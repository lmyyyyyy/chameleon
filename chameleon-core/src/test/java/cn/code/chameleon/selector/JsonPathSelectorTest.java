package cn.code.chameleon.selector;

import org.junit.Test;

import java.util.List;

/**
 * @author liumingyu
 * @create 2018-04-17 下午3:05
 */
public class JsonPathSelectorTest {

    private String text = "{ \"store\": {\n" +
            "    \"book\": [ \n" +
            "      { \"category\": \"reference\",\n" +
            "        \"author\": \"Nigel Rees\",\n" +
            "        \"title\": \"Sayings of the Century\",\n" +
            "        \"price\": 8.95\n" +
            "      },\n" +
            "      { \"category\": \"fiction\",\n" +
            "        \"author\": \"Evelyn Waugh\",\n" +
            "        \"title\": \"Sword of Honour\",\n" +
            "        \"price\": 12.99,\n" +
            "        \"isbn\": \"0-553-21311-3\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"bicycle\": {\n" +
            "      \"color\": \"red\",\n" +
            "      \"price\": 19.95\n" +
            "    }\n" +
            "  }\n" +
            "}";

    @Test
    public void test_json_path() throws Exception {
        JsonPathSelector jsonPathSelector = new JsonPathSelector("$.store.book[*].author");
        String selectStr = jsonPathSelector.select(text);
        System.out.println(selectStr);
        List<String> select = jsonPathSelector.selectList(text);
        System.out.println(select);
        jsonPathSelector = new JsonPathSelector("$.store.book[?(@.category=='reference')].title");
        selectStr = jsonPathSelector.select(text);
        System.out.println(selectStr);
        select = jsonPathSelector.selectList(text);
        System.out.println(select);
        jsonPathSelector = new JsonPathSelector("$.store.book[?(@.category == 'reference')]");
        selectStr = jsonPathSelector.select(text);
        System.out.println(selectStr);
    }
}
