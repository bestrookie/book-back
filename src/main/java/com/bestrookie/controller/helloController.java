package com.bestrookie.controller;


import com.bestrookie.utils.TokenUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

/**
 * @author : bestrookie
 * @date : 15:02 2020/9/24
 */
@RestController
@RequestMapping("/")
public class helloController {
    @RequestMapping("/hello")
    public String hello(HttpServletRequest httpRequest){
        return TokenUtils.token("111","aaa");
    }
}
