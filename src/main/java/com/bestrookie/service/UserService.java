package com.bestrookie.service;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.UserPojo;

/**
 * @author : bestrookie
 * @date : 14:27 2020/10/3
 */
public interface UserService {
    public MyResult queryUserByName(String name,String password);
}
