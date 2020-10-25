package com.bestrookie.service.admin;

import com.bestrookie.model.MyResult;

/**
 * @author : bestrookie
 * @date : 19:27 2020/10/21
 */
public interface AdminService {
    /**
     * 管理员登录
     * @param account
     * @param password
     * @return
     */
    MyResult adminLogin(String account,String password);
}
