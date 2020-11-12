package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.LogPojo;
import com.bestrookie.pojo.ShowPojo;
import com.bestrookie.service.adminlog.AdminLogService;
import com.bestrookie.service.show.ShowService;
import com.bestrookie.utils.InitLogInfoUtils;
import com.bestrookie.utils.IsTrueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 10:58 2020/11/11
 */
@RestController
@RequestMapping("/api/show")
public class ShowController {
    @Autowired
    private ShowService showService;
    @Autowired
    private AdminLogService logService;

    /**
     * 管理员查看所有展示信息
     * @param response 响应参数
     * @param request 请求参数
     * @return 自定义返回值
     */
    @GetMapping("/allshow")
    public MyResult queryAllShow(HttpServletResponse response, HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = showService.queryShowInfo(param);
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
     * 展示信息
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/showinfo")
    public MyResult queryShowInfo(HttpServletRequest request,HttpServletResponse response){
        MyResult result;
        result = showService.queryShow();
        response.setStatus(result.getCode());
        return  result;
    }

    /**
     * 管理员添加展示信息
     * @param response 响应参数
     * @param request 请求参数
     * @param showPojo 展示信息
     * @return 自定义返回类型
     */
    @PostMapping("addshow")
    public MyResult addShowInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody ShowPojo showPojo){
        MyResult result;
        LogPojo log = InitLogInfoUtils.initLogInfo(request,"添加了一条展示信息");
        logService.addAdminLog(log);
        result = showService.addShow(showPojo);
        response.setStatus(result.getCode());
        return  result;
    }

    /**
     * 管理员将信息展示
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/true")
    public MyResult showTrue(HttpServletRequest request,HttpServletResponse response){
        MyResult result;
        if (showService.showCount() < 6){
            LogPojo log = InitLogInfoUtils.initLogInfo(request,"将一条信息添加到展示栏id为："+request.getParameter("showId"));
            logService.addAdminLog(log);
            result = showService.updateShowState(Integer.parseInt(request.getParameter("showId")),true);
        }else {
            result = MyResult.failed("展示栏已满",false,523);
        }
        response.setStatus(result.getCode());
        return  result;
    }

    /**
     * 管理员将展示信息撤销
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/false")
    public MyResult showFalse(HttpServletRequest request,HttpServletResponse response){
        MyResult result;
        if (showService.showCount() > 1){
            LogPojo log = InitLogInfoUtils.initLogInfo(request,"将一条展示栏信息撤销id为："+request.getParameter("showId"));
            logService.addAdminLog(log);
            result = showService.updateShowState(Integer.parseInt(request.getParameter("showId")),false);
        }else {
            result = MyResult.failed("展示栏不能为空",false,523);
        }

        response.setStatus(result.getCode());
        return  result;
    }

    /**
     * 管理员删除展示信息
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/delete")
    public MyResult deleteShow(HttpServletRequest request,HttpServletResponse response){
        MyResult result;
        LogPojo log = InitLogInfoUtils.initLogInfo(request,"删除一条展示信息id为："+request.getParameter("showId"));
        logService.addAdminLog(log);
        result = showService.deleteShow(Integer.parseInt(request.getParameter("showId")));
        response.setStatus(result.getCode());
        return  result;
    }
}
