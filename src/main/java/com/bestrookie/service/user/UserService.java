package com.bestrookie.service.user;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.model.param.UpdatePasswordParam;
import com.bestrookie.pojo.UserPojo;



/**
 * @author : bestrookie
 * @date : 14:27 2020/10/3
 */
public interface UserService {
     /**
      * 账号密码登录
      * @param phone 手机号
      * @param password 密码
      * @return 自定有返回类型
      */
     MyResult queryUserByName(String phone,String password);
     /**
      * 注册一个新用户
      * @param userPojo 账号信息实体
      * @return 自定义返回类型
      */
     MyResult addUserInfo(UserPojo userPojo);
     /**
      * 上传头像到数据库
      * @param imageAddress 图片地址
      * @param phone 图片地址
      * @return 自定义返回类型
      */
     MyResult updateImage(String imageAddress,String phone);
     /**
      * 根据手机号查询用户信息
      * @param phone 手机号
      * @return 自定义返回类型
      */
     MyResult getUserInfo(String phone);
     /**
      * 修改用户名称
      * @param name 用户名称
      * @param phone 手机号
      * @return 自定义返回类型
      */
     MyResult updateUserName(String name,String phone);
     /**
      * 根据用户名查找用户
      * @param userName 用户名称
      * @return 自定义返回类型
      */
     MyResult checkUserName(String userName);
     /**
      * 用户修改密码
      * @param param 密码信息实体
      * @param phone 手机号
      * @return 自定义返回类型
      */
     MyResult updateUserPassword(UpdatePasswordParam param, String phone);
     /**
      * 修改用户虚拟币
      * @param userCoin 虚拟币数量
      * @param phone 手机号
      * @return
      */
     boolean updateUserCoin(int userCoin, String phone);
     /**
      * 查询所有用户
      * @param param 分页参数
      * @return 自定义返回类型
      */
     PageResult queryAllUsers(PageRequestParam param);

     /**
      * 根据用户id查找用户信息
      * @param userId 用户id
      * @return 用户信息
      */
     UserPojo queryUserById(int userId);

}
