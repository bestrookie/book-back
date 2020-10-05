package com.bestrookie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;

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
        String userPhone = "15684135930";
        String code = "146680";
        HashMap<String, String> phone=new HashMap<>();
        phone.put("phone","15684135930");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("15684135930","146680");
        System.out.println(phone.get("phone"));
        System.out.println(hashMap.get("15684135930"));
        redisTemplate.opsForValue().set(userPhone,code);
        System.out.println(redisTemplate.opsForValue().get(phone.get("phone")));
    }
}
