package cn.code.chameleon.service.impl;

import cn.code.chameleon.service.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author liumingyu
 * @create 2018-05-08 下午2:40
 */
@Component
public class RedisClientSingle implements RedisClient {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long time) {
        stringRedisTemplate.opsForValue().set(key, value, time);
    }

    @Override
    public void set(String key, String value, long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, time, unit);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public String getAndSet(String key, String value) {
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public void batchSet(Map<String, String> keyAndValue) {
        stringRedisTemplate.opsForValue().multiSet(keyAndValue);
    }

    @Override
    public Long increment(String key, long number) {
        return stringRedisTemplate.opsForValue().increment(key, number);
    }

    @Override
    public boolean expire(String key, long time, TimeUnit unit) {
        return redisTemplate.boundValueOps(key).expire(time, unit);
    }

    @Override
    public boolean removeExpire(String key) {
        return redisTemplate.boundValueOps(key).persist();
    }

    @Override
    public Long getExpire(String key) {
        return redisTemplate.boundValueOps(key).getExpire();
    }

    @Override
    public void rename(String key, String newKey) {
        redisTemplate.boundValueOps(key).rename(newKey);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void hput(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public void hputAll(String key, Map<String, Object> keyAndValue) {
        redisTemplate.opsForHash().putAll(key, keyAndValue);
    }

    @Override
    public boolean hputIfAbsent(String key, String hashKey, Object value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    @Override
    public Long hdel(String key, String... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    @Override
    public Object hget(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public Map<String, Object> hget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public boolean haskey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    @Override
    public Set<String> hkeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    @Override
    public Long hsize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    @Override
    public Long leftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public Long leftPushAll(String key, Collection<Object> values) {
        return redisTemplate.opsForList().leftPush(key, values);
    }

    @Override
    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public Long rightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public Long rightPushAll(String key, Collection<Object> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    @Override
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public Object popIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    @Override
    public Long lsize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public List<Object> lrange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public Long sadd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public Set<Object> difference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    @Override
    public Long ssize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public boolean isMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public Set<Object> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }
}
