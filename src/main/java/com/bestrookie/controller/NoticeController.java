package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.model.param.ReleaseNoticeParam;
import com.bestrookie.pojo.NoticePojo;
import com.bestrookie.pojo.NoticesReadPojo;
import com.bestrookie.service.notice.NoticeService;
import com.bestrookie.service.noticesread.NoticesReadService;
import com.bestrookie.utils.IsTrueUtils;
import com.bestrookie.utils.TokenUtils;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 20:44 2020/10/27
 */
@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticesReadService readService;

    /**
     * 管理员查看公告
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/querynotice")
    MyResult queryAllNotice(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))){
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = noticeService.queryAllNotice(param);
            if (pageResult == null) {
                result = MyResult.failed("查看公告失败", null, 515);
            } else {
                result = MyResult.success(pageResult);
            }
        }else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return  result;
    }
    /**
     * 删除公告
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/deletenotice")
    MyResult deleteNotice(HttpServletResponse response, HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("noticeId"))){
            result = noticeService.deleteNotice(Integer.parseInt(request.getParameter("noticeId")));
        }else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return result;
    }
    @PostMapping("/releasenotice")
    /**
     * 管理员发布公告
     * @param request
     * @param response
     * @param param
     * @return
     */
    MyResult releaseNotice(HttpServletRequest request,HttpServletResponse response,@RequestBody ReleaseNoticeParam param){
        MyResult result;
        NoticePojo noticePojo = new NoticePojo();
        noticePojo.setNoticeTitle(param.getNoticeTitle());
        noticePojo.setNoticeContent(param.getNoticeContent());
        noticePojo.setNoticeDate(System.currentTimeMillis());
        result = noticeService.releaseNotice(noticePojo);
        response.setStatus(result.getCode());
        return result;
    }
    /**
     * 用户查看公告
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/user/querynotice")
    MyResult userQueryNotice(HttpServletResponse response,HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            int userId = TokenUtils.getId(request.getHeader("authorization"));
            PageResult pageResult = noticeService.queryNotice(param, userId);
            if (pageResult == null) {
                result = MyResult.failed("查看公告失败", null, 515);
            } else {
                result = MyResult.success(pageResult);
            }
        }else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 已读公告
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/user/readnotice")
    MyResult readNotice(HttpServletRequest request,HttpServletResponse response){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("noticeId"))){
            NoticesReadPojo readPojo = new NoticesReadPojo();
            readPojo.setNoticeId(Integer.parseInt(request.getParameter("noticeId")));
            readPojo.setNrDate(System.currentTimeMillis());
            readPojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
            if(readService.isReadNotice(readPojo.getUserId(),readPojo.getNoticeId())){
                result = MyResult.failed("已读",false,515);
            }else{
                result = readService.addNoticeRead(readPojo);
            }
        }else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return result;
    }
}
