package com.bestrookie.controller.admin;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.LogPojo;
import com.bestrookie.service.adminlog.AdminLogService;
import com.bestrookie.service.dynamic.DynamicService;
import com.bestrookie.utils.InitLogInfoUtils;
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
 * @date : 19:55 2020/11/3
 */
@RestController
@RequestMapping("api/admin/dynamic")
public class AdminDynamic {
    @Autowired
    private DynamicService dynamicService;
    @Autowired
    private AdminLogService logService;

    /**
     * 查询所有动态
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/queryall")
    public MyResult queryAllDynamic(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult;
            try{
                int bdId = Integer.parseInt(request.getParameter("bdId"));
                pageResult = dynamicService.queryDynamic(param,bdId, TokenUtils.getId(request.getHeader("authorization")));
            }catch (Exception e){
                pageResult = dynamicService.queryAllDynamic(param);
            }
            if (pageResult != null){
                result = MyResult.success(pageResult);
            }else {
                result = MyResult.failed("查询失败",null,509);
            }
        }else {
            result = MyResult.failed("参数错误",null,412);
        }
        response.setStatus(result.getCode());
        return result;
    }
    /**
     * 删除动态
     * @param response 响应参数
     * @param request 请求参数
     * @return 自定义返回类型
     */
    @GetMapping("/deletedynamic")
    public MyResult deleteDynamic(HttpServletResponse response,HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("dynamicId"))){
            String desLog = "删除一条动态，动态id为："+request.getParameter("dynamicId");
            LogPojo log = InitLogInfoUtils.initLogInfo(request,desLog);
            logService.addAdminLog(log);
            result = dynamicService.deleteDynamic(Integer.parseInt(request.getParameter("dynamicId")));
        }else {
            result = MyResult.failed("参数错误",null,412);
        }
        response.setStatus(result.getCode());
        return result;
    }

}
