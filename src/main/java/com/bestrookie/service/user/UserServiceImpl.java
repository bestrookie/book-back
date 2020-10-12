package com.bestrookie.service.user;

import com.bestrookie.mapper.UserMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.param.UpdatePasswordParam;
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

    /**
     * 账号密码登录
     * @param phone
     * @param password
     * @return
     */
    @Override
    public MyResult queryUserByName(String phone,String password) {
        UserPojo userPojo = userMapper.queryUserByName(phone);
        if (userPojo!=null && userPojo.getPassword().equals(password)){
            String token = TokenUtils.token(phone,userPojo.getUserId());
            HashMap<String, String> hashMap = SImageUtils.sImage(token, userPojo.getImage());
            //保留用户最后一条token
            String key = "T"+ phone;
            redisTemplate.opsForValue().set(key,token);
            return MyResult.success(hashMap,"登陆成功");
        }else{
            return MyResult.failed("账号或者密码错误");
        }
    }
    /**
     * 注册一个新用户
     * @param userPojo
     * @return
     */
    @Override
    public MyResult addUserInfo(UserPojo userPojo) {
        boolean flg = userMapper.addUserInfo(userPojo);
         userPojo = userMapper.queryUserByName(userPojo.getUserPhone());
        if (flg == true){
            String token = TokenUtils.token(userPojo.getUserPhone(),userPojo.getUserId());
            HashMap<String, String> hashMap = SImageUtils.sImage(token, userPojo.getImage());
            String key = "T"+ userPojo.getUserPhone();
            redisTemplate.opsForValue().set(key,token);
            return MyResult.success(hashMap,"注册成功");
        }else
            return MyResult.failed("注册失败");
    }
    /**
     * 设置头像
     * @param imageAddress
     * @param phone
     * @return
     */
    @Override
    public MyResult updateImage(String imageAddress,String phone) {
        boolean flg = userMapper.updateImage(imageAddress, phone);
        if (flg){
            return MyResult.success(imageAddress,"保存成功");
        }else
            return MyResult.failed("保存失败",null,406);
    }
    /**
     * 根据手机号查询用户的信息
     * @param phone
     * @return
     */
    @Override
    public MyResult getUserInfo(String phone) {
        UserPojo userPojo = userMapper.queryUserByName(phone);
        if (userPojo!=null){
            if (userPojo.getPassword().isEmpty()){
                userPojo.setPassword("empty");
            }else{
                userPojo.setPassword("exist");
            }return MyResult.success(userPojo);
        }
            return MyResult.failed("查询失败");

    }
    /**
     * 修改用户名称
     * @param userName
     * @param phone
     * @return
     */
    @Override
    public MyResult updateUserName(String userName, String phone) {
        if (checkUserName(userName).getCode() == 404){
            boolean flg = userMapper.updateUserName(userName, phone);
            if (flg){
                return MyResult.success(userName,"修改成功");
            }else
                return MyResult.failed("修改失败",null,406);
        }
        return MyResult.failed("用户名已存在",null,406);

    }
    /**
     * 根据用户名查找用户
     * @param userName
     * @return
     */
    @Override
    public MyResult checkUserName(String userName) {
        if (userName != null){
            UserPojo userPojo = userMapper.checkUserByName(userName);
            if (userPojo != null){
                return MyResult.success(userPojo,"查找成功");
            }else
                return MyResult.failed("未找到该用户",null,404);
        }
        return MyResult.failed("姓名不能为空");
    }
    /**
     * 用户修改密码
     * @param param
     * @param phone
     * @return
     */
    public MyResult updateUserPassword(UpdatePasswordParam param,String phone){
        if (!param.getCode().equals(redisTemplate.opsForValue().get(phone))){
            return MyResult.failed("验证码错误",null,405);
        }else {
            redisTemplate.opsForValue().getOperations().delete(phone);
            if (userMapper.updateUserPassword(param.getPassword(),phone)){
                return MyResult.success(null,"修改成功");
            }else {
                return MyResult.failed("修改失败",null,406);
            }
        }
    }
    /**
     * 修改用户虚拟币
     * @param userCoin
     * @param phone
     * @return
     */
    @Override
    public MyResult updateUserCoin(int userCoin, String phone) {
        boolean flg = userMapper.updateUserCoin(userCoin, phone);
        if (flg){
            return MyResult.success(userCoin,"修改成功");
        }else {
            return  MyResult.failed("修改失败",null,406);
        }
    }

}
