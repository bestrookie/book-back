package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.AddDiscussionParam;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookDiscussionsPojo;
import com.bestrookie.service.bookdiscussion.BookDiscussionsService;
import com.bestrookie.utils.SensitiveWordUtils;
import com.bestrookie.utils.TokenUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 16:33 2020/10/13
 */
@RestController
@RequestMapping("/api/discussions")
public class BookDiscussionsController {
    @Autowired
    private BookDiscussionsService bookDiscussionsService;
    /**
     * 创建书圈
     * @param response
     * @param request
     * @param param
     * @return
     */
    @SneakyThrows
    @PostMapping("/adddiscussion")
    public MyResult addDiscussion(HttpServletResponse response, HttpServletRequest request, AddDiscussionParam param){
        int userId = TokenUtils.getId(request.getHeader("authorization"));
        MyResult result;
        BookDiscussionsPojo bookDiscussionsPojo = null;
        if (SensitiveWordUtils.contains(param.getDbName()) && SensitiveWordUtils.contains(param.getDbDesc())){
            bookDiscussionsPojo.setUserId(userId);
            bookDiscussionsPojo.setBdName(param.getDbName());
            bookDiscussionsPojo.setBdDes(param.getDbDesc());
            bookDiscussionsPojo.setBdDate(System.currentTimeMillis());
            result = bookDiscussionsService.addBookDiscussion(bookDiscussionsPojo);
        }else {
            result =  MyResult.failed("输入信息包含敏感词汇",null,408);
        }
        return  result;
    }
    /**
     * 分页查找书圈信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/querydiscussion")
    public MyResult queryBookDiscussions(HttpServletRequest request,HttpServletResponse response){
        MyResult myResult = null;
        if (Integer.parseInt(request.getParameter("pageNumber")) < 0 || Integer.parseInt(request.getParameter("pageSize")) < 0){
            myResult =  MyResult.failed("分页信息错误",null,407);
        }else{
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult result = bookDiscussionsService.queryDiscussion(param);
            if (result.getContent() == null){
                myResult = MyResult.failed("查询书圈失败",null,507);
            }else {
                myResult = MyResult.success(result);
            }
        }
        response.setStatus(myResult.getCode());
        return myResult;
    }

    /**
     * 根据id查询书圈
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/querybyid")
    public MyResult queryBookDiscussionById(HttpServletResponse response,HttpServletRequest request){
        MyResult result = null;
        int userId = TokenUtils.getId(request.getHeader("authorization"));
        if (request.getParameter("discussionId") != null){
            int discussionId = Integer.parseInt(request.getParameter("discussionId"));
            if (discussionId < 0 ){
                result = MyResult.failed("id信息错误",null,408);
            }else {
                result = bookDiscussionsService.queryDiscussionById(userId,discussionId);
            }
        }else {
            result = MyResult.failed("id不能为空",null,408);
        }
        response.setStatus(result.getCode());
        return result;
    }
}
