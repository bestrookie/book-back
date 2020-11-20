package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.service.booklimit.BookLimitService;
import com.bestrookie.utils.IsTrueUtils;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 20:26 2020/11/15
 */
@RestController
@RequestMapping("/api/limit")
public class BookLimitController {
    @Autowired
    private BookLimitService limitService;


    /**
     * 全部解锁
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/alllimit")
    public MyResult getBookAllLimit(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        if (limitService.isExistBookLimit(TokenUtils.getId(request.getHeader("authorization")),bookId)){
            result = limitService.partAllLimit(TokenUtils.getId(request.getHeader("authorization")),bookId);
        }else {
            result = limitService.getBookAllLimit(TokenUtils.getId(request.getHeader("authorization")),bookId);
        }

        response.setStatus(result.getCode());
        return  result;
    }
    @GetMapping("/querylimit")
    public MyResult queryBookLimit(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        result = limitService.queryBookLimit(TokenUtils.getId(request.getHeader("authorization")),bookId);
        response.setStatus(result.getCode());
        return  result;
    }
    @GetMapping("/partlimit")
    public MyResult getBookLimit(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        result = limitService.getBookLimit(TokenUtils.getId(request.getHeader("authorization")),bookId);
        response.setStatus(result.getCode());
        return  result;
    }
    @GetMapping("/queryhistory")
    public MyResult queryBookHistory(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = limitService.queryBookLimitHistory(param, TokenUtils.getId(request.getHeader("authorization")));
            if (pageResult == null) {
                result = MyResult.failed("解锁历史失败", null, 528);
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
