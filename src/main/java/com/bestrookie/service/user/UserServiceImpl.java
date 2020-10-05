package com.bestrookie.service.user;

import com.bestrookie.mapper.UserMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.UserPojo;
import com.bestrookie.utils.SImageUtil;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Override
    public MyResult queryUserByName(String phone,String password) {
        UserPojo userPojo = userMapper.queryUserByName(phone);
        if (userPojo!=null && userPojo.getPassword().equals(password)){
            HashMap<String, String> hashMap = SImageUtil.sImage(TokenUtils.token(phone), userPojo.getImage());
            return MyResult.success(hashMap,"登陆成功");
        }else{
            return MyResult.failed("账号或者密码错误");
        }
    }
    @Override
    public MyResult addUserInfo(UserPojo userPojo) {
        boolean flg = userMapper.addUserInfo(userPojo);
        if (flg == true){
            HashMap<String, String> hashMap = SImageUtil.sImage(TokenUtils.token(userPojo.getUserPhone()), userPojo.getImage());
            return MyResult.success(hashMap,"注册成功");
        }else
            return MyResult.failed("注册失败");
    }

}
