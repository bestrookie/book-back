package com.bestrookie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author : bestrookie
 * @date : 14:46 2020/10/5
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Test
    public void set(){
        String userPhone = "15684135931";
        String code = "146680";
        redisTemplate.opsForValue().set(userPhone,code,2*5, TimeUnit.SECONDS);
    }
}
