package com.easy.trip.controller.test;

import lombok.SneakyThrows;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

import static com.easy.trip.common.enums.RedisKeyEnum.LOGIN_TOPIC;

/**
 * @author Steven
 * @date 2022年10月02日 18:32
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @Resource
    private RedisTemplate redisTemplate;


    @GetMapping("/redis")
    public void testRedisPublishSubscribe() {

        redisTemplate.execute((RedisCallback) connection -> {
            try {
                connection.subscribe((message, pattern) ->
                        System.out.format("channel:%s , body: %s",new String(message.getChannel()),new String(message.getBody())),
                        LOGIN_TOPIC.getKey().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        });

        redisTemplate.execute((RedisCallback) connection -> {
            try {
                String message = "开始抢购啦。。。";
                Long publish = connection.publish(LOGIN_TOPIC.getKey().getBytes("UTF-8"), message.getBytes("UTF-8"));
                return publish;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        });


    }
}
