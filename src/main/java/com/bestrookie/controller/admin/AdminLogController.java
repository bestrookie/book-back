package com.bestrookie.controller.admin;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.service.adminlog.AdminLogService;
import com.bestrookie.utils.IsTrueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 14:45 2020/11/3
 */
@RestController
@RequestMapping("/api/admin/log")
public class AdminLogController {
    @Autowired
    private AdminLogService logService;
    /**
     * 查询日志
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/querylog")
    public MyResult queryLog(HttpServletRequest request, HttpServletResponse response) {
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = logService.queryLogInfo(param);
            if (pageResult != null) {
                result = MyResult.success(pageResult);
            } else {
                result = MyResult.failed("查询日志失败", false, 516);
            }
        } else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return result;
    }
}
