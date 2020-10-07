package com.bestrookie.service.user;

import com.bestrookie.mapper.UserMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.UserPojo;
import com.bestrookie.utils.SImageUtils;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author : bestrookie
 * @date : 14:45 2020/10/3
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public MyResult queryUserByName(String phone,String password) {
        UserPojo userPojo = userMapper.queryUserByName(phone);
        if (userPojo!=null && userPojo.getPassword().equals(password)){
            String token = TokenUtils.token(phone);
            HashMap<String, String> hashMap = SImageUtils.sImage(token, userPojo.getImage());
            //保留用户最后一条token
            String key = "T"+ phone;
            redisTemplate.opsForValue().set(key,token);
            return MyResult.success(hashMap,"登陆成功");
        }else{
            return MyResult.failed("账号或者密码错误");
        }
    }
    @Override
    public MyResult addUserInfo(UserPojo userPojo) {
        boolean flg = userMapper.addUserInfo(userPojo);
        if (flg == true){
            String token = TokenUtils.token(userPojo.getUserPhone());
            HashMap<String, String> hashMap = SImageUtils.sImage(token, userPojo.getImage());
            String key = "T"+ userPojo.getUserPhone();
            redisTemplate.opsForValue().set(key,token);
            return MyResult.success(hashMap,"注册成功");
        }else
            return MyResult.failed("注册失败");
    }

    @Override
    public MyResult updateImage(String imageAddress,String phone) {
        boolean flg = userMapper.updateImage(imageAddress, phone);
        if (flg){
            return MyResult.success(imageAddress,"保存成功");
        }else
            return MyResult.failed("保存失败",null,406);
    }

}
