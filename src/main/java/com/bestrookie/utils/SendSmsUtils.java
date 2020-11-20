package com.bestrookie.utils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.stream.JsonToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author : bestrookie
 * @date : 14:45 2020/10/4
 */
@Slf4j
public class SendSmsUtils {
    private static final String SIGN_NAME = "书源网";
    private static final String TEMPLATE_CODE = "SMS_204106333";


    public static HashMap<String, String> send(String phone,String filePath){
        String[] secretKey = new String[3];
        int i = 0;
        Set<String> strings = readTxtByLine(filePath);
        for (String string : strings) {
            secretKey[i] = string;
            i++;
        }

        String code = RandomStringUtils.randomNumeric(6);
        System.out.println(code);
        HashMap<String, String> result = new HashMap();
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", secretKey[1], secretKey[0]);
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
        request.putQueryParameter("TemplateParam","{code:"+"'"+code+"'"+"}");
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
    public static Set<String> readTxtByLine(String path){
        Set<String> keyWordSet = new HashSet<String>();
        File file=new File(path);
        if(!file.exists()){
            return keyWordSet;
        }
        BufferedReader reader=null;
        String temp;
        try{
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            while((temp=reader.readLine())!=null){
                keyWordSet.add(temp);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(reader!=null){
                try{
                    reader.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return keyWordSet;
    }

//    public static void main(String[] args) {
//        Set<String> key = readTxtByLine("D:\\resources\\password\\key.txt");
//        String[] secretKey = new String[3];
//        int i = 0;
//        for (String s : key) {
//            secretKey[i] = s;
//            i++;
//            System.out.println(s);
//        }
//        System.out.println("=====================");
//        for (String s : secretKey) {
//            System.out.println(s);
//        }
//    }
}
