package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.service.books.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 14:47 2020/11/19
 */
@RestController
@RequestMapping("/api/cf")
public class ItemCFController {
    @Autowired
    private BookService bookService;
    /**
     * 分类榜
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/cf")
    public MyResult queryTypeTop(HttpServletResponse response){
        int i = 1;
        MyResult result = bookService.booksOutByType(i);
        response.setStatus(result.getCode());
        return  result;
    }
}
