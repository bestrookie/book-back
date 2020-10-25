package com.bestrookie.service.user;

import com.bestrookie.model.MyResult;

/**
 * @author : bestrookie
 * @date : 20:43 2020/10/4
 */
public interface PLoginService {
     /**
      * 手机登录
      * @param phone
      * @param code
      * @return
      */
     MyResult pLogin(String phone,String code);
}
