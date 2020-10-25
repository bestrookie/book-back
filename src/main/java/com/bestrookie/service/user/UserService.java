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
      * @param phone
      * @param password
      * @return
      */
     MyResult queryUserByName(String phone,String password);
     /**
      * 注册一个新用户
      * @param userPojo
      * @return
      */
     MyResult addUserInfo(UserPojo userPojo);
     /**
      * 上传头像到数据库
      * @param imageAddress
      * @param phone
      * @return
      */
     MyResult updateImage(String imageAddress,String phone);
     /**
      * 根据手机号查询用户信息
      * @param phone
      * @return
      */
     MyResult getUserInfo(String phone);
     /**
      * 修改用户名称
      * @param name
      * @param phone
      * @return
      */
     MyResult updateUserName(String name,String phone);
     /**
      * 根据用户名查找用户
      * @param userName
      * @return
      */
     MyResult checkUserName(String userName);
     /**
      * 用户修改密码
      * @param param
      * @param phone
      * @return
      */
     MyResult updateUserPassword(UpdatePasswordParam param, String phone);
     /**
      * 修改用户虚拟币
      * @param userCoin
      * @param phone
      * @return
      */
     MyResult updateUserCoin(int userCoin,String phone);

     /**
      * 查询所有用户
      * @param param
      * @return
      */
     PageResult queryAllUsers(PageRequestParam param);

}
