package com.bestrookie.service.user;

import com.bestrookie.model.MyResult;

/**
 * @author : bestrookie
 * @date : 20:43 2020/10/4
 */
public interface PLoginService {
     MyResult pLogin(String phone,String code);
}
