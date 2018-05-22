package cn.code.chameleon.spider.pipeline;


import cn.code.chameleon.carrier.Results;
import cn.code.chameleon.carrier.Task;
import cn.code.chameleon.pipeline.Pipeline;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-05-15 上午10:27
 */
public class RedisPipeline implements Pipeline {

    protected JedisPool pool;

    public RedisPipeline() {
        this("localhost");
    }

    public RedisPipeline(String host) {
        this(new JedisPool(new JedisPoolConfig(), host));
    }

    public RedisPipeline(JedisPool pool) {
        this.pool = pool;
    }

    @Override
    public void process(Results results, Task task) {
        Jedis jedis = pool.getResource();
        try {
            for (Map.Entry<String, Object> entry : results.getAll().entrySet()) {
                jedis.hset(task.getUUID(), entry.getKey(), entry.getValue().toString());
            }
        } finally {
            pool.returnResource(jedis);
        }
    }
}
