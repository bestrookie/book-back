package com.bestrookie.controller.admin;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.BookPojo;
import com.bestrookie.pojo.LogPojo;
import com.bestrookie.service.adminlog.AdminLogService;
import com.bestrookie.service.books.BookService;
import com.bestrookie.utils.InitLogInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 15:18 2020/11/13
 */
@RestController
@RequestMapping("/api/adminbook")
public class AdminBookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AdminLogService logService;
    @GetMapping("/true")
    public MyResult trueBook(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        String desLog = "修改了一本书的审核状态，书籍id为："+request.getParameter("bookId");
        LogPojo log = InitLogInfoUtils.initLogInfo(request,desLog);
        logService.addAdminLog(log);
        result = bookService.updateStatus(Integer.parseInt(request.getParameter("bookId")),true);
        response.setStatus(result.getCode());
        return result;
    }
    @GetMapping("/false")
    public MyResult falseBook(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        String desLog = "修改了一本书的审核状态，书籍id为："+request.getParameter("bookId");
        LogPojo log = InitLogInfoUtils.initLogInfo(request,desLog);
        logService.addAdminLog(log);
        result = bookService.updateStatus(Integer.parseInt(request.getParameter("bookId")),false);
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 修改书籍id
     * @param response 请求参数
     * @param request 响应参数
     * @param bookPojo 书籍信息
     * @return 自定义返回类型
     */
    @PutMapping("/update")
    MyResult updateBook(HttpServletResponse response, HttpServletRequest request, @RequestBody BookPojo bookPojo){
        MyResult result;
        String desLog = "修改了一本书的信息，书籍id为："+bookPojo.getBookId();
        LogPojo log = InitLogInfoUtils.initLogInfo(request,desLog);
        logService.addAdminLog(log);
        result = bookService.updateBookInfo(bookPojo);
        response.setStatus(result.getCode());
        return result;
    }
}
