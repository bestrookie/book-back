package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.service.message.MessageService;
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
 * @date : 14:50 2020/10/26
 */
@RestController
@RequestMapping("/api/msg")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 获取消息列表
     *
     * @param response ：响应参数
     * @param request  ： 请求参数
     * @return 结果 状态码
     */
    @GetMapping("/querymsg")
    public MyResult queryMessage(HttpServletResponse response, HttpServletRequest request) {
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            int userId = TokenUtils.getId(request.getHeader("authorization"));
            PageResult pageResult = messageService.queryMsgByUserId(param, userId);
            if (pageResult == null) {
                result = MyResult.failed("查看消息失败", null, 514);
            } else {
                result = MyResult.success(pageResult);
            }
        } else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return result;

    }

    /**
     * 查看系统消息
     * @param response ： 响应参数
     * @param request ： 请求参数
     * @return 自定义返回类型
     */
    @GetMapping("/querysystem")
    public MyResult querySystemMsg(HttpServletResponse response, HttpServletRequest request) {
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            int userId = TokenUtils.getId(request.getHeader("authorization"));
            PageResult pageResult = messageService.querySystemMsg(param,userId);
            if (pageResult == null) {
                result = MyResult.failed("查看系统消息失败", null, 514);
            } else {
                result = MyResult.success(pageResult);
            }
        } else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return result;

    }


    /**
     * 已读消息
     *
     * @param request  ： 请求参数
     * @param response ： 响应参数
     * @return 结果 状态码
     */
    @GetMapping("/hasread")
    public MyResult hasReadMsg(HttpServletRequest request, HttpServletResponse response) {
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("msgId"))) {
            result = messageService.hasReadMsg(Integer.parseInt(request.getParameter("msgId")));
        } else {
            result = MyResult.failed("参数类型错误", false, 412);
        }
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 一键已读
     *
     * @param response ： 响应参数
     * @param request  ： 请求参数
     * @return 自定义结果集
     */
    @GetMapping("/readall")
    public MyResult hasReadMsgAll(HttpServletResponse response, HttpServletRequest request) {
        MyResult result;
        result = messageService.hasReadMsgAll(TokenUtils.getId(request.getHeader("authorization")));
        response.setStatus(result.getCode());
        return result;
    }
}
