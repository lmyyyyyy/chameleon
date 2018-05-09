package cn.code.chameleon.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author liumingyu
 * @create 2018-05-08 下午12:40
 */
public interface RedisClient {

    void set(String key, String value);

    void set(String key, String value, long time);

    void set(String key, String value, long time, TimeUnit unit);

    String get(String key);

    String getAndSet(String key, String value);

    void batchSet(Map<String, String> keyAndValue);

    Long increment(String key, long number);

    boolean expire(String key, long time, TimeUnit unit);

    boolean removeExpire(String key);

    Long getExpire(String key);

    void rename(String key, String newKey);

    void delete(String key);

    void hput(String key, String hashKey, Object value);

    void hputAll(String key, Map<String, Object> keyAndValue);

    boolean hputIfAbsent(String key, String hashKey, Object value);

    Long hdel(String key, String... hashKeys);

    Object hget(String key, String hashKey);

    Map<String, Object> hget(String key);

    boolean haskey(String key, String hashKey);

    Set<String> hkeys(String key);

    Long hsize(String key);

    Long leftPush(String key, Object value);

    Long leftPushAll(String key, Collection<Object> values);

    Object leftPop(String key);

    Long rightPush(String key, Object value);

    Long rightPushAll(String key, Collection<Object> value);

    Object rightPop(String key);

    Object popIndex(String key, long index);

    Long lsize(String key);

    List<Object> lrange(String key, long start, long end);

    Long sadd(String key, Object... values);

    Set<Object> difference(String key, String otherKey);

    Long ssize(String key);

    boolean isMember(String key, Object value);

    Set<Object> members(String key);

}
