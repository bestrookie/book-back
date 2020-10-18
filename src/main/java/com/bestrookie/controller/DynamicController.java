package com.bestrookie.controller;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.model.param.ReleaseDynamicParam;
import com.bestrookie.pojo.DynamicPojo;
import com.bestrookie.service.dynamic.DynamicService;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 20:25 2020/10/15
 */
@RestController
@RequestMapping("/api/dynamic")
public class DynamicController {
    @Autowired
    private DynamicService dynamicService;
    /**
     * 分页查询动态
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/querydynamic")
    public MyResult queryDynamic(HttpServletRequest request, HttpServletResponse response){
        MyResult result = null;
        if (Integer.parseInt(request.getParameter("pageNumber")) < 0 || Integer.parseInt(request.getParameter("pageSize")) < 0 || Integer.parseInt(request.getParameter("bdId")) < 0){
            result = MyResult.failed("分页信息错误",null,409);
        }else {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = dynamicService.queryDynamic(param,Integer.parseInt(request.getParameter("bdId")),TokenUtils.getId(request.getHeader("authorization")));
            if (pageResult == null){
                result = MyResult.failed("查询动态失败",null,509);
            }else {
                result = MyResult.success(pageResult);
            }
        }
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 发布动态
     * @param response
     * @param request
     * @param param
     * @return
     */
    @PostMapping("/releasedynamic")
    public MyResult releaseDynamic(HttpServletResponse response, HttpServletRequest request, @RequestBody ReleaseDynamicParam param){
        MyResult result;
        if (param == null){
            result = MyResult.failed("发布信息不能为空",null,409);
        }else {
            DynamicPojo dynamicPojo = new DynamicPojo();
            dynamicPojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
            dynamicPojo.setDDate(System.currentTimeMillis());
            dynamicPojo.setDContent(param.getContent());
            dynamicPojo.setDAbstract(param.getAbstracts());
            dynamicPojo.setBdId(param.getBdId());
            result = dynamicService.releaseDynamic(dynamicPojo);
        }
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 根据id查询动态
     * @param response
     * @param request
     * @return
     */
    @GetMapping("querybyid")
    public MyResult queryDynamicById(HttpServletRequest request,HttpServletResponse response){
        MyResult result = null;
        if (Integer.parseInt(request.getParameter("dynamicId")) > 0){
            result = dynamicService.queryDynamicById(Integer.parseInt(request.getParameter("dynamicId")),TokenUtils.getId(request.getHeader("authorization")));
        }else {
            result = MyResult.failed("参数错误",null,409);
        }
        response.setStatus(result.getCode());
        return  result;
    }
    @GetMapping("/deletedynamic")
    public MyResult deleteDynamic(HttpServletResponse response,HttpServletRequest request){
        MyResult result = null;
        if (Integer.parseInt(request.getParameter("dynamicId")) > 0){
            result = dynamicService.deleteDynamicById(Integer.parseInt(request.getParameter("dynamicId")),TokenUtils.getId(request.getHeader("authorization")));
        }else {
            result = MyResult.failed("参数错误",null,409);
        }
        response.setStatus(result.getCode());
        return result;
    }
}
