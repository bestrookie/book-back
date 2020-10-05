package com.bestrookie.utils;
import com.bestrookie.pojo.UserPojo;

/**
 * @author : bestrookie
 * @date : 17:06 2020/10/4
 */
public class InitUser {
    public static UserPojo initUser(String phone){
        UserPojo userPojo = new UserPojo();
        userPojo.setUserName("用户"+phone.substring(phone.length()-4));
        userPojo.setImage("image/01.png");
        userPojo.setUserPhone(phone);
        return userPojo;
    }
}
