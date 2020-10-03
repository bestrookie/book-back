package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : bestrookie
 * @date : 14:29 2020/10/3
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public MyResult login(String userName,String password){
        return userService.queryUserByName(userName,password);
    }
}
