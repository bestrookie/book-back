package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookHistoryPojo;
import com.bestrookie.service.bookhistory.BookHistoryService;
import com.bestrookie.utils.IsTrueUtils;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 20:42 2020/11/17
 */
@RestController
@RequestMapping("/api/history")
public class BookHistoryController {
    @Autowired
    private BookHistoryService historyService;
    @GetMapping("query")
    public MyResult queryHistory(HttpServletRequest request,HttpServletResponse response,@RequestParam int bookId){
        MyResult result;
        result = historyService.queryHistory(TokenUtils.getId(request.getHeader("authorization")),bookId);
        response.setStatus(result.getCode());
        return  result;
    }
    @GetMapping("/queryall")
    public MyResult queryAllHistory(HttpServletResponse response,HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = historyService.queryAllHistory(param, TokenUtils.getId(request.getHeader("authorization")));
            if (pageResult == null) {
                result = MyResult.failed("查看历史记录失败", null, 529);
            } else {
                result = MyResult.success(pageResult);
            }
        }else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return result;
    }
    @GetMapping("/update")
    public MyResult updateHistory(HttpServletRequest request,HttpServletResponse response,@RequestParam int bookId,@RequestParam int page){
        MyResult result;
        if (historyService.isHistory(TokenUtils.getId(request.getHeader("authorization")),bookId))
        {
            BookHistoryPojo historyPojo = new BookHistoryPojo();
            historyPojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
            historyPojo.setHistoryDate(System.currentTimeMillis());
            historyPojo.setBookId(bookId);
            historyPojo.setHistoryPage(page);
            result = historyService.updateHistory(historyPojo);

        }else {
            BookHistoryPojo historyPojo = new BookHistoryPojo();
            historyPojo.setBookId(bookId);
            historyPojo.setHistoryDate(System.currentTimeMillis());
            historyPojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
            historyPojo.setHistoryPage(page);
            result = historyService.addHistory(historyPojo);
        }
        response.setStatus(result.getCode());
        return result;

    }
}
