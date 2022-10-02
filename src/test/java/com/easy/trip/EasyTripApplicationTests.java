package com.easy.trip;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@SpringBootTest(classes = EasyTripApplication.class)
@RunWith(value = SpringRunner.class)
@ActiveProfiles("dev")
class EasyTripApplicationTests {


    @Resource
    private RedisTemplate redisTemplate;


    @Test
    void contextLoads() {


        redisTemplate.execute((RedisCallback) connection -> {
            try {
                String channel = "guangbo";
                String message = "开始抢购啦。。。";
                Long publish = connection.publish(channel.getBytes("UTF-8"), message.getBytes("UTF-8"));
                return publish;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        });

        redisTemplate.execute((RedisCallback) connection -> {
            connection.subscribe((message, pattern) -> {
                byte[] channel = message.getChannel();
                byte[] body = message.getBody();
                System.out.format("channel : %s ,message : %s", new String(channel), new String(body));
            });

            return null;
        });
    }


}
