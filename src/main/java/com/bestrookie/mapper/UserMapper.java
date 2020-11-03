package com.bestrookie.mapper;

import com.bestrookie.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author : bestrookie
 * @date : 10:09 2020/10/3
 */
@Repository
@Mapper
public interface UserMapper {
     /**
      * 根据手机号查询账户信息
      * @param phone 手机号
      * @return 账号信息实体
      */
     UserPojo queryUserByName(String phone);
     /**
      * 增加一个新用户
      * @param userPojo 用户信息实体
      * @return 是否添加成功
      */
     boolean addUserInfo(UserPojo userPojo);
     /**
      * 根据手机号更新账户的头像
      * @param imageAddress 图片地址
      * @param phone 手机号
      * @return 是否修改成功
      */
     boolean updateImage(String imageAddress,String phone);
     /**
      * 修改用户名称
      * @param userName 用户名称
      * @param phone 手机号
      * @return 是否修改成功
      */
     boolean updateUserName(String userName,String phone);
     /**
      * 根据姓名查询用户
      * @param userName 用户名称
      * @return 自定义返回类型
      */
     List<UserPojo> checkUserByName(String userName);
     /**
      * 修改密码
      * @param userPassword 密码
      * @param phone 手机号
      * @return 是否修改成功
      */
     boolean updateUserPassword(String userPassword,String phone);
     /**
      * 修改用户虚拟币
      * @param userCoin 虚拟币
      * @param phone 手机号
      * @return 是否修改成功
      */
     boolean updateUserCoin(@Param("userCoin") int userCoin, @Param("phone") String phone);

     /**
      * 查询所有用户
      * @return 用户实体
      */
     List<UserPojo> queryAllUsers();
}
