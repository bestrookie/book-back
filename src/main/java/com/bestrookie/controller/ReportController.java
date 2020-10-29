package com.bestrookie.controller;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.model.param.ReportParam;
import com.bestrookie.pojo.ReportPojo;
import com.bestrookie.service.report.ReportService;
import com.bestrookie.utils.IsTrueUtils;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 10:41 2020/10/29
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    /**
     * 查看所有举报信息
     * @param response 响应参数
     * @param request 请求参数
     * @return 自定义返回结果集
     */
    @GetMapping("/queryreport")
    public MyResult queryReportInfo(HttpServletResponse response, HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))){
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = reportService.queryReports(param,Integer.parseInt(request.getParameter("type")));
            if (pageResult == null) {
                result = MyResult.failed("查看举报消息", null, 516);
            } else {
                result = MyResult.success(pageResult);
            }
        }else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(516);
        return result;
    }
    /**
     * 举报
     * @param request 请求参数
     * @param response 响应参数
     * @param param 表格参数
     * @return 自定义返回值
     */
    @PostMapping("/addreport")
    public MyResult addReportInfo(HttpServletRequest request, HttpServletResponse response,@RequestBody ReportParam param) {
        MyResult result;
        System.out.println(param.toString());
        if (param.getDescription() != null && param.getReportType() >= 0 && param.getReportType() < 3){
            ReportPojo reportPojo = new ReportPojo();
            reportPojo.setReportDate(System.currentTimeMillis());
            reportPojo.setReportDes(param.getDescription());
            reportPojo.setTargetId(param.getTargetId());
            reportPojo.setReportType(param.getReportType());
            reportPojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
            result = reportService.addReportInfo(reportPojo);
        }else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return  result;
    }
    /**
     * 处理举报
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/solvereport")
    public MyResult solveReport(HttpServletResponse response,HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("reportId"))){
            result = reportService.solveReport(Integer.parseInt(request.getParameter("reportId")));
        }else {
            result = MyResult.failed("参数错误",false,412);
        }
        response.setStatus(result.getCode());
        return result;
    }
}
