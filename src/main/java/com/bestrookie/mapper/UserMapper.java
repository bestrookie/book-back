package com.bestrookie.mapper;

import com.bestrookie.pojo.UserPojo;
import org.apache.catalina.User;
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
      * @return
      */
     UserPojo queryUserByName(String phone);
     /**
      * 增加一个新用户
      * @param userPojo
      * @return
      */
     boolean addUserInfo(UserPojo userPojo);
     /**
      * 根据手机号更新账户的头像
      * @param imageAddress
      * @param phone
      * @return
      */
     boolean updateImage(String imageAddress,String phone);
     /**
      * 修改用户名称
      * @param userName
      * @param phone
      * @return
      */
     boolean updateUserName(String userName,String phone);
     /**
      * 根据姓名查询用户
      * @param userName
      * @return
      */
     UserPojo checkUserByName(String userName);
     /**
      * 修改密码
      * @param userPassword
      * @param phone
      * @return
      */
     boolean updateUserPassword(String userPassword,String phone);
     /**
      * 修改用户虚拟币
      * @param userCoin
      * @param phone
      * @return
      */
     boolean updateUserCoin(@Param("userCoin") int userCoin, @Param("phone") String phone);

     /**
      * 查询所有用户
      * @return
      */
     List<UserPojo> queryAllUsers();
}
