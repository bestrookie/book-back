package com.bestrookie.service.sms;

import com.bestrookie.service.sms.GetSmsService;
import com.bestrookie.utils.SendSms;
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
        return SendSms.send(phone);
    }
}
