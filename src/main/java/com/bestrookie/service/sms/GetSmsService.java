package com.bestrookie.service.sms;

import java.util.HashMap;

/**
 * @author : bestrookie
 * @date : 20:28 2020/10/4
 */
public interface GetSmsService {
    /**
     * 获取验证码
     * @param phone 手机号
     * @return 验证码
     */
    HashMap<String, String> getSms(String phone);
}
