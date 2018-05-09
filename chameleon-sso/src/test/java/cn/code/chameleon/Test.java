package cn.code.chameleon;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author liumingyu
 * @create 2018-05-08 下午2:19
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Test {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @org.junit.Test
    public void save() {
        stringRedisTemplate.opsForValue().set("aaa", "asdf");
        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
    }
}
