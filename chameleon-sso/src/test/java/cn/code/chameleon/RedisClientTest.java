package cn.code.chameleon;

import cn.code.chameleon.service.RedisClient;
import cn.code.chameleon.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author liumingyu
 * @create 2018-05-08 下午3:37
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisClientTest {

    @Autowired
    private RedisClient redisClient;

    @Test
    public void setTest() throws Exception {
        redisClient.set("bendan", "1");
        System.out.println(redisClient.get("bendan"));
    }

    @Test
    public void lpushTest() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("嘿嘿嘿");
        redisClient.leftPush("users", user);
        redisClient.rightPush("users", 1);
        System.out.println("user size : " + redisClient.lsize("users"));
        System.out.println("user : " + redisClient.lrange("users", 0, -1));
    }

    @Test
    public void spushTest() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("嘿嘿嘿");
        redisClient.sadd("user", user, user);
        redisClient.sadd("user", "aaa");
        redisClient.sadd("user", "aaa");
        System.out.println(redisClient.ssize("user"));
        System.out.println(redisClient.members("user"));
    }
}
