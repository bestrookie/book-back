package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.service.usertop.UserTopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 16:58 2020/11/14
 */
@RestController
@RequestMapping("/api/top")
public class UserTopController {
    @Autowired
    private UserTopService topService;
    @GetMapping("query")
    public MyResult queryTop(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        result = topService.queryUserTop();
        response.setStatus(result.getCode());
        return  result;
    }
}
