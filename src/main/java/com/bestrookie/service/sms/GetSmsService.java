package com.bestrookie.service.sms;

import java.util.HashMap;

/**
 * @author : bestrookie
 * @date : 20:28 2020/10/4
 */
public interface GetSmsService {
    public HashMap<String, String> GetSms(String phone);
}
