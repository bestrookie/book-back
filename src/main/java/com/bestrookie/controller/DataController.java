package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.service.data.DataService;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author : bestrookie
 * @date : 9:47 2020/11/18
 */
@RestController
@RequestMapping("/api/data")
public class DataController {
    @Autowired
    private DataService dataService;
    @GetMapping("/user/data")
    public MyResult userBookData(HttpServletResponse response, HttpServletRequest request){
        MyResult result;
        result = dataService.userBookData(TokenUtils.getId(request.getHeader("authorization")));
        response.setStatus(result.getCode());
        return result;
    }
    @GetMapping("/admin/bookdata")
    public MyResult bookData(HttpServletResponse response, HttpServletRequest request){
        MyResult result;
        result = dataService.bookData();
        response.setStatus(result.getCode());
        return result;
    }
    @GetMapping("/admin/typedata")
    public MyResult typeData(HttpServletResponse response, HttpServletRequest request){
        MyResult result;
        result = dataService.bookTypeData();
        response.setStatus(result.getCode());
        return result;
    }
    @GetMapping("/admin/discussion")
    public MyResult discussionData(HttpServletResponse response, HttpServletRequest request){
        MyResult result;
        result = dataService.bookDiscussionData();
        response.setStatus(result.getCode());
        return result;
    }
}
