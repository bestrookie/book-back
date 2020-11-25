package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.param.LoginUser;
import com.bestrookie.model.param.UpdatePasswordParam;
import com.bestrookie.service.sms.GetSmsService;
import com.bestrookie.service.user.ImageUploadService;
import com.bestrookie.service.user.PLoginService;
import com.bestrookie.service.user.UserService;
import com.bestrookie.utils.SensitiveWordUtils;
import com.bestrookie.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${file.banWord-path}")
    private String wordPath;
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
     * @param user 前端表格参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @PostMapping("/login")
    public MyResult login(@RequestBody LoginUser user, HttpServletResponse response) {
        MyResult myResult = userService.queryUserByName(user.getPhone(), user.getPassword());
        response.setStatus(myResult.getCode());
        return myResult;

    }

    /**
     * 手机号登录
     * @param verify 手机号 验证码
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @PostMapping("/plogin")
    public MyResult register(@RequestBody HashMap<String, String> verify, HttpServletResponse response) {
        MyResult myResult = pLoginService.pLogin(verify.get("phone"), verify.get("code"));
        response.setStatus(myResult.getCode());
        return myResult;
    }

    /**
     * 获取验证码
     * @param phone 手机号
     * @return 自定义返回类型
     */
    @PostMapping("/getsms")
    public MyResult getSms(@RequestBody HashMap<String, String> phone, HttpServletResponse response) {
        MyResult result;
        HashMap<String, String> hashMap = getSmsService.getSms(phone.get("phone"));
        String userPhone = phone.get("phone");
        String code = hashMap.get(userPhone);
        redisTemplate.opsForValue().set(userPhone, code, 5 * 60, TimeUnit.SECONDS);
        if (hashMap.size() == 0) {
            result =  MyResult.failed("获取验证码失败",false,500);
        }else {
            result = MyResult.success(true);
        }
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 上传图片
     * @param file 照片文件
     * @return 自定义返回类型
     */
    @PostMapping("/imageupload")
    public MyResult imageUpload(MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws IOException {
        MyResult myResult;
        MyResult result = imageUploadService.imageUpload(file);
        if (result.getCode() != 200) {
            myResult = result;
        } else {
            String token = request.getHeader("authorization");
            myResult = userService.updateImage((String) result.getObj(), TokenUtils.getInfo(token));
        }
        response.setStatus(myResult.getCode());
        return myResult;

    }

    /**
     * 查看个人信息
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/getinfo")
    public MyResult getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String phone = TokenUtils.getInfo(request.getHeader("authorization"));
        MyResult myResult = userService.getUserInfo(phone);
        response.setStatus(myResult.getCode());
        return myResult;
    }

    /**
     * 修改用户名称
     * @param userName 用户名称
     * @param response 响应参数
     * @param request 请求参数
     * @return 自定义返回类型
     */
    @PostMapping("/updateusername")
    public MyResult updateUserName(@RequestBody HashMap<String, String> userName, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String phone = TokenUtils.getInfo(request.getHeader("authorization"));
        MyResult myResult;
        if (userName.get("userName") != null) {
            SensitiveWordUtils.init(wordPath);
            if (SensitiveWordUtils.contains(userName.get("userName"))) {
                myResult = MyResult.failed("内容违规", null, 406);
            } else {
                myResult = userService.updateUserName(userName.get("userName"), phone);
            }
        } else {
            myResult = MyResult.failed("姓名不能为空", null, 406);
        }
        response.setStatus(myResult.getCode());
        return myResult;
    }

    /**
     * 用户修改密码
     * @param param  前端表格数据
     * @param response 响应参数
     * @param request 请求参数
     * @return 自定义结果集
     */
    @PostMapping("/updatepassword")
    public MyResult updateUserPassword(@RequestBody UpdatePasswordParam param, HttpServletResponse response, HttpServletRequest request) {
        String phone = TokenUtils.getInfo(request.getHeader("authorization"));
        if (param == null) {
            response.setStatus(406);
            return MyResult.failed("输入不能为空", null, 406);
        }
        MyResult myResult = userService.updateUserPassword(param, phone);
        response.setStatus(myResult.getCode());
        return myResult;
    }

    /**
     * 用户注销
     * @param request 请求参数
     * @return 自定义返回结果集
     */
    @GetMapping("/logout")
    public MyResult logOut(HttpServletRequest request) {
        String phone = "T" + TokenUtils.getInfo(request.getHeader("authorization"));
        try {
            redisTemplate.opsForValue().getOperations().delete(phone);
            return MyResult.success(true);
        } catch (Exception e) {
            return MyResult.failed("注销失败", false, 506);
        }
    }

}
