package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.param.LoginUser;
import com.bestrookie.model.param.UpdatePasswordParam;
import com.bestrookie.service.sms.GetSmsService;
import com.bestrookie.service.user.ImageUploadService;
import com.bestrookie.service.user.PLoginService;
import com.bestrookie.service.user.UserService;
import com.bestrookie.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author : bestrookie
 * @date : 14:29 2020/10/3
 */
@Slf4j
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
    @Autowired
    private ImageUploadService imageUploadService;

    /**
     * 账号密码登录
     * @return
     */
    @PostMapping("/login")
    public MyResult login(@RequestBody LoginUser user, HttpServletResponse response){
            MyResult myResult = userService.queryUserByName(user.getPhone(), user.getPassword());
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
        redisTemplate.opsForValue().set(userPhone,code,5*60, TimeUnit.SECONDS);
        if (hashMap == null){
            response.setStatus(500);
            return "failed";
        }
        return "success";
    }
    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("/imageupload")
    public MyResult imageUpload(MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws IOException {

        MyResult result = imageUploadService.imageUpload(file);
        String token = request.getHeader("authorization");
        MyResult myResult = userService.updateImage((String) result.getObj(), TokenUtils.getInfo(token));
        response.setStatus(myResult.getCode());
        return myResult;

    }
    /**
     * 查看个人信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/getinfo")
    public MyResult getUserInfo(HttpServletRequest request,HttpServletResponse response){
        String phone = TokenUtils.getInfo(request.getHeader("authorization"));
        MyResult myResult = userService.getUserInfo(phone);
        response.setStatus(myResult.getCode());
        return myResult;
    }
    /**
     * 修改用户名称
     * @param userName
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/updateusername")
    public MyResult updateUserName(@RequestBody HashMap<String, String> userName,HttpServletResponse response,HttpServletRequest request){
        String phone = TokenUtils.getInfo(request.getHeader("authorization"));
        MyResult myResult = new MyResult();
        System.out.println(userName.get("userName"));
        System.out.println(userName);
        if (userName.get("userName") != null){
            myResult = userService.updateUserName(userName.get("userName"), phone);
        }else {
            myResult = MyResult.failed("姓名不能为空",null,406);
        }
        response.setStatus(myResult.getCode());
        return myResult;
    }
    /**
     * 用户修改密码
     * @param param
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/updatepassword")
    public MyResult updateUserPassword(@RequestBody UpdatePasswordParam param,HttpServletResponse response,HttpServletRequest request){
        String phone = TokenUtils.getInfo(request.getHeader("authorization"));
        if (param == null){
            response.setStatus(406);
            return MyResult.failed("输入不能为空",null,406);
        }
        MyResult myResult = userService.updateUserPassword(param, phone);
        response.setStatus(myResult.getCode());
        return  myResult;
    }
    /**
     * 用户注销
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public boolean logOut(HttpServletResponse response,HttpServletRequest request){
        String phone = "T" + TokenUtils.getInfo(request.getHeader("authorization"));
        if (phone == null || phone.isEmpty()){
            return false;
        }else {
            if (redisTemplate.opsForValue().getOperations().delete(phone)){
                return true;
            }else {
                return false;
            }
        }
    }

}
