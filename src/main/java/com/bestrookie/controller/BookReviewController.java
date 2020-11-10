package com.bestrookie.controller;


import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.BookReviewParam;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookReviewPojo;
import com.bestrookie.service.bookreview.BookReviewService;
import com.bestrookie.utils.IsTrueUtils;
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
 * @date : 10:12 2020/11/10
 */
@RestController
@RequestMapping("/api/bookreview")
public class BookReviewController {
    @Autowired
    private BookReviewService bookReviewService;
    @Value("${file.banWord-path}")
    private String wordPath;

    /**
     * 发表评论
     * @param request 请求参数
     * @param response 响应参数
     * @param reviewParam 评论内容
     * @return 自定义返回类型
     */
    @SneakyThrows
    @PostMapping("/postreview")
    public MyResult postComment(HttpServletRequest request, HttpServletResponse response, @RequestBody BookReviewParam reviewParam){
        MyResult result;
        SensitiveWordUtils.init(wordPath);
        if (!(SensitiveWordUtils.contains(reviewParam.getReviewContent()))){
            BookReviewPojo reviewPojo = new BookReviewPojo();
            reviewPojo.setValue(reviewParam.getValue());
            reviewPojo.setBookId(reviewParam.getBookId());
            reviewPojo.setReviewContent(reviewParam.getReviewContent());
            reviewPojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
            reviewPojo.setReviewDate(System.currentTimeMillis());
            result = bookReviewService.postComment(reviewPojo);
        }else {
            result = MyResult.failed("含有敏感词汇",false,412);
        }
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 查看书籍品论
     * @param response 响应参数
     * @param request 请求参数
     * @return 自定义返回类型
     */
    @GetMapping("/querybookreview")
    public MyResult queryAllBookReview(HttpServletResponse response,HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize")) && IsTrueUtils.isTrue(request.getParameter("bookId"))){
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = bookReviewService.queryAllBookReview(param, Integer.parseInt(request.getParameter("bookId")));
            if (pageResult == null) {
                result = MyResult.failed("查看书籍评论", null, 521);
            } else {
                result = MyResult.success(pageResult);
            }
        }else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return  result;
    }
}
