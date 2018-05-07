package cn.code.chameleon.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     *
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将对象转成Json格式字符串
     *
     * @param obj 待转换对象
     * @return JSON字符串, 如果对象为null则返回null
     * @throws JsonProcessingException
     */
    public static String toJsonStr(Object obj) throws JsonProcessingException {
        if (obj == null) {
            return null;
        }

        return MAPPER.writeValueAsString(obj);
    }

    /**
     * 将对象转成Json格式字符串
     *
     * @param obj 待转换对象
     * @return JSON字符串, 如果对象为null或转换异常则返回""
     */
    public static String toJsonStrWithEmptyDefault(Object obj) {
        String jsonStr = "";

        try {
            jsonStr = toJsonStr(obj);
        } catch (Exception e) {
            LOGGER.warn("将对象转成Json字符串抛出异常, obj: {}", obj);
        }

        return jsonStr;
    }

}
