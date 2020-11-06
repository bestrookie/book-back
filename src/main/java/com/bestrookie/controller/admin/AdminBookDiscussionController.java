package com.bestrookie.controller.admin;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.LogPojo;
import com.bestrookie.service.adminlog.AdminLogService;
import com.bestrookie.service.bookdiscussion.BookDiscussionsService;
import com.bestrookie.utils.InitLogInfoUtils;
import com.bestrookie.utils.IsTrueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 16:32 2020/11/3
 */
@RestController
@RequestMapping("/api/admin/adminbd")
public class AdminBookDiscussionController {
    @Autowired
    private BookDiscussionsService bookDiscussionsService;
    @Autowired
    private AdminLogService logService;

    /**
     * 查询所有书圈
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/querydiscussion")
    public MyResult queryBookDiscussion(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = bookDiscussionsService.queryDiscussion(param);
            if (pageResult != null){
                result = MyResult.success(pageResult);
            }else {
                result = MyResult.failed("查询失败",null,508);
            }
        }else {
            result = MyResult.failed("参数错误",null,412);
        }
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 删除书圈
     * @param response 响应参数
     * @param request 请求参数
     * @return 自定义返回类型
     */
    @GetMapping("/deletediscussion")
    public MyResult deleteDiscussion(HttpServletResponse response,HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("discussionId"))){
            String desLog = "删除了一条书圈，书圈id为："+request.getParameter("discussionId");
            LogPojo log = InitLogInfoUtils.initLogInfo(request,desLog);
            logService.addAdminLog(log);
            result = bookDiscussionsService.deleteDiscussion(Integer.parseInt(request.getParameter("discussionId")));
        }else{
            result = MyResult.failed("参数错误",null,412);
        }
        response.setStatus(result.getCode());
        return result;
    }
}
