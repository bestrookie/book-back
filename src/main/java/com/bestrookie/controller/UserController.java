package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.UserPojo;
import com.bestrookie.service.sms.GetSmsService;
import com.bestrookie.service.user.PLoginService;
import com.bestrookie.service.user.UserService;
import com.bestrookie.utils.InitUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import java.net.http.HttpResponse;
import java.util.HashMap;

/**
 * @author : bestrookie
 * @date : 14:29 2020/10/3
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private GetSmsService getSmsService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private PLoginService pLoginService;

    /**
     * 账号密码登录
     * @return
     */
    @PostMapping("/login")
    public MyResult login(@RequestBody HashMap<String, String> user, HttpServletResponse response){
            MyResult myResult = userService.queryUserByName(user.get("phone"), user.get("password"));
            response.setStatus(myResult.getCode());
            return myResult;

    }
    /**
     * 手机登录
     * @return
     */
    @PostMapping("/plogin")
    public MyResult register(@RequestBody HashMap<String, String> verify,HttpServletResponse response){
        MyResult myResult = pLoginService.pLogin(verify.get("phone"), verify.get("code"));
        response.setStatus(myResult.getCode());
        return myResult;
    }
    /**
     * 获取验证码
     * @param phone
     * @return
     */
    @PostMapping("/getsms")
    public String GetSms(@RequestBody HashMap<String, String> phone,HttpServletResponse response){
        HashMap<String, String> hashMap = getSmsService.GetSms(phone.get("phone"));
        String userPhone = phone.get("phone");
        String code = hashMap.get(userPhone);
        redisTemplate.opsForValue().set(userPhone,code);
        if (hashMap == null){
            response.setStatus(500);
            return "failed";
        }
        return "success";
    }
}
