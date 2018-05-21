package cn.code.chameleon.spider.scheduler.component;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.scheduler.component.DuplicateRemover;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author liumingyu
 * @create 2018-05-21 下午3:10
 */
public class RedisBloomFilterRemover implements DuplicateRemover {

    protected JedisPool pool;

    private static final String BLOOM_PREFIX = "bloom-";

    private static final int[] seeds = new int[]{3, 7, 11, 13, 31, 37, 55};

    private BloomHash[] func = new BloomHash[seeds.length];

    public RedisBloomFilterRemover(String host) {
        this(new JedisPool(new JedisPoolConfig(), host));
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new BloomHash(2 << 26, seeds[i]);
        }
    }

    public RedisBloomFilterRemover(JedisPool pool) {
        this.pool = pool;
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new BloomHash(2 << 26, seeds[i]);
        }
    }

    @Override
    public boolean isDuplicate(Request request, Task task) {
        boolean ret = true;
        for (BloomHash f : func) {
            ret = ret && getBit(getBloomKey(task), f.hash(request.getUrl()));
        }
        if (!ret) {
            for (BloomHash f : func) {
                setBit(getBloomKey(task), f.hash(request.getUrl()), true);
            }
        }
        return ret;
    }

    @Override
    public void resetDuplicate(Task task) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(getBloomKey(task));
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public int getDuplicateCounts(Task task) {
        return 0;
    }

    private boolean setBit(String key, int offset, boolean value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.setbit(key, offset, value);
        } catch (Exception e) {
            return true;
        }
    }

    private boolean getBit(String key, int offset) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.getbit(key, offset);
        } catch (Exception e) {
            return true;
        }
    }

    protected String getBloomKey(Task task) {
        return BLOOM_PREFIX + task.getUUID();
    }

    /**
     * 一个简单的hash算法类，输出int类型hash值
     */
    public static class BloomHash {

        private int cap;
        private int seed;

        public BloomHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        public int hash(String value) {
            int result = 0;
            int len = value.length();
            for (int i = 0; i < len; i++) {
                result = seed * result + value.charAt(i);
            }
            return (cap - 1) & result;
        }
    }
}
