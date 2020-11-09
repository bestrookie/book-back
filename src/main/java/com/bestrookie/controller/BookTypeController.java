package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.service.type.BookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 19:45 2020/11/7
 */
@RestController
@RequestMapping("/api/booktype")
public class BookTypeController {
    @Autowired
    private BookTypeService bookTypeService;
    @GetMapping("/querytype")
    MyResult queryBookType(HttpServletResponse response){
        MyResult result;
        result = bookTypeService.queryBookType();
        response.setStatus(result.getCode());
        return result;
    }
}
