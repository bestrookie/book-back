package com.bestrookie.service.user;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.UserPojo;


/**
 * @author : bestrookie
 * @date : 14:27 2020/10/3
 */
public interface UserService {
     MyResult queryUserByName(String phone,String password);
     MyResult addUserInfo(UserPojo userPojo);
     MyResult updateImage(String imageAddress,String phone);
}
