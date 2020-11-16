package com.bestrookie.service.user;

import com.bestrookie.mapper.UserMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.model.param.UpdatePasswordParam;
import com.bestrookie.pojo.UserPojo;
import com.bestrookie.utils.PageUtils;
import com.bestrookie.utils.SImageUtils;
import com.bestrookie.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
     * @param phone 手机号
     * @param password 密码
     * @return 自定义返回结果集
     */
    @Override
    public MyResult queryUserByName(String phone,String password) {
        UserPojo userPojo = userMapper.queryUserByName(phone);
        if (userPojo!=null && Objects.equals(userPojo.getPassword(),password)){
            String token = TokenUtils.token(phone,userPojo.getUserId(),0);
            HashMap<String, String> hashMap = SImageUtils.sImage(token, userPojo.getImage());
            //保留用户最后一条token
            String key = "T"+ phone;
            assert token != null;
            redisTemplate.opsForValue().set(key,token);
            return MyResult.success(hashMap,"登陆成功");
        }else{
            return MyResult.failed("账号或者密码错误");
        }
    }
    /**
     * 注册一个新用户
     * @param userPojo 用户信息实体
     * @return 自定义返回结果集
     */
    @Override
    public MyResult addUserInfo(UserPojo userPojo) {
        boolean flg = userMapper.addUserInfo(userPojo);
        userPojo = userMapper.queryUserByName(userPojo.getUserPhone());
        if (flg){
            String token = TokenUtils.token(userPojo.getUserPhone(),userPojo.getUserId(),0);
            HashMap<String, String> hashMap = SImageUtils.sImage(token, userPojo.getImage());
            String key = "T"+ userPojo.getUserPhone();
            assert token != null;
            redisTemplate.opsForValue().set(key,token);
            return MyResult.success(hashMap,"注册成功");
        }else {
            return MyResult.failed("注册失败");
        }
    }
    /**
     * 设置头像
     * @param imageAddress 图片地址
     * @param phone 手机号
     * @return 自定义返回类型
     */
    @Override
    public MyResult updateImage(String imageAddress,String phone) {
        boolean flg = userMapper.updateImage(imageAddress, phone);
        if (flg){
            return MyResult.success(imageAddress,"保存成功");
        }else{
            return MyResult.failed("保存失败",null,406);
        }
    }
    /**
     * 根据手机号查询用户的信息
     * @param phone 手机号
     * @return 自定义返回类型
     */
    @Override
    public MyResult getUserInfo(String phone) {
        UserPojo userPojo = userMapper.queryUserByName(phone);
        if (userPojo!=null){
            if (userPojo.getPassword() == null){
                userPojo.setPassword("empty");
            }else{
                userPojo.setPassword("exist");
            }return MyResult.success(userPojo);
        }else {
            return MyResult.failed("查询失败");
        }

    }
    /**
     * 修改用户名称
     * @param userName 用户名称
     * @param phone 手机号
     * @return 自定义返回类型
     */
    @Override
    public MyResult updateUserName(String userName, String phone) {
        if (checkUserName(userName).getCode() == 404){
            boolean flg = userMapper.updateUserName(userName, phone);
            if (flg){
                return MyResult.success(userName,"修改成功");
            }else{
                return MyResult.failed("修改失败",null,406);
            }
        }
        return MyResult.failed("用户名已存在",null,406);

    }
    /**
     * 根据用户名查找用户
     * @param userName 用户名称
     * @return 自定义返回类型
     */
    @Override
    public MyResult checkUserName(String userName) {
        if (userName != null){
            List<UserPojo> userPojo = userMapper.checkUserByName(userName);
            if (userPojo != null){
                return MyResult.success(userPojo,"查找成功");
            }else{
                return MyResult.failed("未找到该用户",null,404);
            }
        }
        return MyResult.failed("姓名不能为空");
    }
    /**
     * 用户修改密码
     * @param param 前端表格参数
     * @param phone 手机号
     * @return 自定义返回类型
     */
    @Override
    public MyResult updateUserPassword(UpdatePasswordParam param, String phone){
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
     * @param userCoin 虚拟币数量
     * @param phone 手机号
     * @return
     */
    @Override
    public boolean updateUserCoin(int userCoin, String phone) {
        return userMapper.updateUserCoin(userCoin, phone);
    }

    /**
     * 查询所有用户
     * @param param 分页参数
     * @return 分页结果
     */
    @Override
    public PageResult queryAllUsers(PageRequestParam param) {
        return PageUtils.getPageResult(param,getUserPageInfo(param));
    }

    /**
     * 根据用户id查找信息
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public UserPojo queryUserById(int userId) {
        return userMapper.queryUserById(userId);
    }

    /**
     * 调用分页插件完成分页
     * @param param 分页参数
     * @return 分页结果
     */
    private PageInfo<UserPojo> getUserPageInfo(PageRequestParam param){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<UserPojo> userPojo = userMapper.queryAllUsers();
        return new PageInfo<>(userPojo);
    }

}
