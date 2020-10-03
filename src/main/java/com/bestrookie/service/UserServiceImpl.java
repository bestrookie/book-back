package com.bestrookie.service;

import com.bestrookie.mapper.UserMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.UserPojo;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author : bestrookie
 * @date : 14:45 2020/10/3
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public MyResult queryUserByName(String name,String password) {
        UserPojo userPojo = userMapper.queryUserByName(name);
        if (userPojo!=null){
            if (userPojo.getPassword().equals(password)){
                return MyResult.success(TokenUtils.token(name, password),"登陆成功");
            }else {
                return MyResult.failed("账号或者密码错误");
            }
        }else{
            return MyResult.failed("账号不存在");
        }
    }
}
