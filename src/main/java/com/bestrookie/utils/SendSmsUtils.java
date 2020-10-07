package com.bestrookie.utils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.Random;

/**
 * @author : bestrookie
 * @date : 14:45 2020/10/4
 */
@Slf4j
public class SendSmsUtils {
    private static final String ACCESS_KEY_ID = "LTAI4G4mzNHrS38Gioo6LnNU";
    private static final String SECRET = "aEQfTi1nztc7nlaiHGpypDvkuxPCUH";
    private static final String SIGN_NAME = "书源网";
    private static final String TEMPLATE_CODE = "SMS_204106333";


    public static HashMap<String, String> send(String phone){
        String code = RandomStringUtils.randomNumeric(6);
        HashMap<String, String> result = new HashMap();
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", SIGN_NAME);
        request.putQueryParameter("TemplateCode", TEMPLATE_CODE);
        request.putQueryParameter("TemplateParam","{code:"+code+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            result.put(phone,code);
        } catch (ServerException e) {
            result.put("msg","SmsError");
            e.printStackTrace();
        } catch (ClientException e) {
            result.put("msg","SeverError");
            e.printStackTrace();
        }
        return result;
    }
}
