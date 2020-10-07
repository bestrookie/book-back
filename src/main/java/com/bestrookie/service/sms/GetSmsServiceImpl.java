package com.bestrookie.service.sms;

import com.bestrookie.utils.SendSmsUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author : bestrookie
 * @date : 20:29 2020/10/4
 */
@Service
public class GetSmsServiceImpl implements GetSmsService {
    @Override
    public HashMap<String, String> GetSms(String phone) {
        return SendSmsUtils.send(phone);
    }
}
