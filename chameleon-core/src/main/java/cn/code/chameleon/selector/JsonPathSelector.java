package cn.code.chameleon.selector;



import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-13 下午1:50
 */
public class JsonPathSelector implements Selector {

    private String jsonStr;

    private JsonPath jsonPath;

    public JsonPathSelector(String jsonStr) {
        this.jsonStr = jsonStr;
        this.jsonPath = JsonPath.compile(jsonStr);
    }

    @Override
    public String select(String text) {
        Object obj = jsonPath.read(text);
        if (obj == null) {
            return null;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            if (list != null && !list.isEmpty()) {
                return toString(list.iterator().next());
            }
        }
        return obj.toString();
    }

    @Override
    public List<String> selectList(String text) {
        List<String> list = new ArrayList<String>();
        Object object = jsonPath.read(text);
        if (object == null) {
            return list;
        }
        if (object instanceof List) {
            List<Object> items = (List<Object>) object;
            for (Object item : items) {
                list.add(toString(item));
            }
        } else {
            list.add(toString(object));
        }
        return list;
    }

    private String toString(Object obj) {
        if (obj instanceof Map) {
            return JSON.toJSONString(obj);
        } else {
            return String.valueOf(obj);
        }
    }
}
