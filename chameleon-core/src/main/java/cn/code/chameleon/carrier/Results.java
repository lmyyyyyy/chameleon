package cn.code.chameleon.carrier;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-04-09 下午3:39
 */
public class Results {

    private Map<String, Object> fields = new LinkedHashMap<>();

    private Request request;

    private boolean isJump;

    public <T> Results put(String key, T t) {
        fields.put(key, t);
        return this;
    }

    public <T> T get(String key) {
        Object obj = fields.get(key);
        if (obj == null) {
            return null;
        }
        return (T) obj;
    }

    public Map<String, Object> getAll() {
        return fields;
    }

    public Request getRequest() {
        return request;
    }

    public Results setRequest(Request request) {
        this.request = request;
        return this;
    }

    public boolean isJump() {
        return isJump;
    }

    public Results setJump(boolean jump) {
        isJump = jump;
        return this;
    }
}
