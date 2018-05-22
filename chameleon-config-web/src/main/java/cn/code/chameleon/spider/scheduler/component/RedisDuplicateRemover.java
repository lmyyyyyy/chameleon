package cn.code.chameleon.spider.scheduler.component;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.scheduler.component.DuplicateRemover;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author liumingyu
 * @create 2018-05-21 下午5:15
 */
public class RedisDuplicateRemover implements DuplicateRemover {

    protected JedisPool pool;

    private static final String SET_PREFIX = "set-";

    public RedisDuplicateRemover(String host) {
        this(new JedisPool(new JedisPoolConfig(), host));
    }

    public RedisDuplicateRemover(JedisPool pool) {
        this.pool = pool;
    }

    @Override
    public boolean isDuplicate(Request request, Task task) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.sadd(getSetKey(task), request.getUrl()) == 0;
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public void resetDuplicate(Task task) {
        Jedis jedis = pool.getResource();
        try {
            jedis.del(getSetKey(task));
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public int getDuplicateCounts(Task task) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.scard(getSetKey(task)).intValue();
        } finally {
            pool.returnResource(jedis);
        }
    }

    protected String getSetKey(Task task) {
        return SET_PREFIX + task.getUUID();
    }
}
