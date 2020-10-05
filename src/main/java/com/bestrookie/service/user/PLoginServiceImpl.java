package com.bestrookie.service.user;

import com.bestrookie.mapper.UserMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.UserPojo;
import com.bestrookie.utils.InitUser;
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
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("token",TokenUtils.token(phone));
                hashMap.put("image",userPojo.getImage());
                return MyResult.success(hashMap);
            }else {
                UserPojo userPojo = InitUser.initUser(phone);
                return userService.addUserInfo(userPojo);
            }
        }
    }
}
