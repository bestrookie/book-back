package com.bestrookie.service.admin;

import com.bestrookie.mapper.UserMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.UserPojo;
import com.bestrookie.utils.SImageUtils;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author : bestrookie
 * @date : 19:29 2020/10/21
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    /**
     * 管理员登录
     * @param account
     * @param password
     * @return
     */
    @Override
    public MyResult adminLogin(String account, String password) {
        UserPojo userPojo = userMapper.queryUserByName(account);
        if (userPojo != null && Objects.equals(userPojo.getPassword(),password) && userPojo.getRole() == 1){
            String token = TokenUtils.token(account,userPojo.getUserId());
            HashMap<String, String> hashMap = SImageUtils.sImage(token, userPojo.getImage());
            String key = "T"+account;
            redisTemplate.opsForValue().set(key,token);
            return MyResult.success(hashMap,"登陆成功");
        }else {
            return MyResult.failed("账号或者密码错误");
        }
    }
}
