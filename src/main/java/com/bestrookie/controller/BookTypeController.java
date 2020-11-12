package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookTypePojo;
import com.bestrookie.pojo.LogPojo;
import com.bestrookie.service.adminlog.AdminLogService;
import com.bestrookie.service.type.BookTypeService;
import com.bestrookie.utils.InitLogInfoUtils;
import com.bestrookie.utils.IsTrueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 19:45 2020/11/7
 */
@RestController
@RequestMapping("/api/booktype")
public class BookTypeController {
    @Autowired
    private BookTypeService bookTypeService;
    @Autowired
    private AdminLogService logService;
    @GetMapping("/querytype")
    MyResult queryBookType(HttpServletResponse response){
        MyResult result;
        result = bookTypeService.queryBookType();
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 分页查看书籍类型
     * @param request 请求参数
     * @param response 行营参数
     * @return 自定义返回类型
     */
    @GetMapping("/all")
    MyResult allType(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = bookTypeService.queryAllType(param);
            if (pageResult == null) {
                result = MyResult.failed("查看书籍类型失败", null, 518);
            } else {
                result = MyResult.success(pageResult);
            }
        }else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return result;
    }
    @PostMapping("add")
    MyResult addType(HttpServletRequest request, HttpServletResponse response, @RequestBody BookTypePojo bookTypePojo){
        MyResult result;
        result = bookTypeService.addBookType(bookTypePojo);
        String desLog = "添加了一个书籍类型，类型id为："+bookTypePojo.getTypeId();
        LogPojo log = InitLogInfoUtils.initLogInfo(request,desLog);
        logService.addAdminLog(log);
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 修改类型
     * @param request 请求参数
     * @param response 响应参乎
     * @param bookTypePojo 书籍类型信息
     * @return 自定义返回类型
     */
    @PutMapping("update")
    MyResult updateType(HttpServletRequest request, HttpServletResponse response, @RequestBody BookTypePojo bookTypePojo){
        MyResult result;
        result = bookTypeService.updateBookType(bookTypePojo);
        String desLog = "修改了一个书籍类型，类型id为："+bookTypePojo.getTypeId();
        LogPojo log = InitLogInfoUtils.initLogInfo(request,desLog);
        logService.addAdminLog(log);
        response.setStatus(result.getCode());
        return result;
    }

}
