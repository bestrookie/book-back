package com.bestrookie.service.user;

import com.bestrookie.mapper.UserMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.UserPojo;
import com.bestrookie.utils.InitUserUtils;
import com.bestrookie.utils.SImageUtils;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author : bestrookie
 * @date : 20:44 2020/10/4
 */
@Service
public class PLoginServiceImpl implements PLoginService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Override
    public MyResult pLogin(String phone, String code) {
        if (!code.equals(redisTemplate.opsForValue().get(phone))){
            return MyResult.failed("验证码错误",null,405);
        }else {
            redisTemplate.opsForValue().getOperations().delete(phone);
            if (userMapper.queryUserByName(phone)!=null){
                UserPojo userPojo = userMapper.queryUserByName(phone);
                String token = TokenUtils.token(phone,userPojo.getUserId());
                HashMap<String, String> hashMap = SImageUtils.sImage(token,userPojo.getImage());
                String key = "T"+ userPojo.getUserPhone();
                redisTemplate.opsForValue().set(key,token);
                return MyResult.success(hashMap);
            }else {
                UserPojo userPojo = InitUserUtils.initUser(phone);
                return userService.addUserInfo(userPojo);
            }
        }
    }
}
