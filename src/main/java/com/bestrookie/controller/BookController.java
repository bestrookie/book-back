package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.BookPojo;
import com.bestrookie.service.async.AsyncService;
import com.bestrookie.service.books.BookService;
import com.bestrookie.utils.IsTrueUtils;
import com.bestrookie.utils.SendSmsUtils;
import com.bestrookie.utils.SensitiveWordUtils;
import com.bestrookie.utils.TokenUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 19:27 2020/11/7
 */
@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AsyncService asyncService;
    @Value("${file.banWord-path}")
    private String wordPath;

    /**
     * 上传书籍
     * @param response 响应参数
     * @param bookPojo 书籍信息
     * @param request 请求参数
     * @return 自定义返回类型
     */
    @SneakyThrows
    @PostMapping("uploadbook")
    public MyResult uploadBook(HttpServletRequest request,HttpServletResponse response, @RequestBody BookPojo bookPojo){
        MyResult result;
        SensitiveWordUtils.init(wordPath);
        if (!(SensitiveWordUtils.contains(bookPojo.getBookName()) && SensitiveWordUtils.contains(bookPojo.getPublisher()) && SensitiveWordUtils.contains(bookPojo.getAuthor()))){
            bookPojo.setUploadDate(System.currentTimeMillis());
            bookPojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
            asyncService.isBookLegal(bookPojo);
            result = bookService.uploadBook(bookPojo);
        }else {
            result = MyResult.failed("参数错误",null,412);
        }

        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 查询pdf是否存在
     * @param response 响应参数
     * @param request 请求参数
     * @return 自定义返回类型
     */
    @GetMapping("isexist")
    public MyResult isPdfExists(HttpServletResponse response , HttpServletRequest request){
        MyResult result;
        result = bookService.queryPdfExists(request.getParameter("identity"));
        response.setStatus(result.getCode());
        return result;
    }
}
