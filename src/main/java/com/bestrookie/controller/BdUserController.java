package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.BdUserPojo;
import com.bestrookie.service.bduser.BdUserService;
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
 * @date : 20:47 2020/10/14
 */
@RestController
@RequestMapping("api/bduser")
public class BdUserController {
    @Autowired
    private BdUserService bdUserService;

    /**
     * 加入书圈
     * @param request
     * @return
     */
    @GetMapping("/join")
    public MyResult joinDiscussion(HttpServletRequest request,HttpServletResponse response) {
        BdUserPojo bdUser = new BdUserPojo();
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("bdId"))){
            bdUser.setBdId(Integer.parseInt(request.getParameter("bdId")));
            bdUser.setBduDate(System.currentTimeMillis());
            bdUser.setUserId(TokenUtils.getId(request.getHeader("authorization")));
            result = bdUserService.joinDiscussion(bdUser);
        }else {
            result = MyResult.failed("参数错误",null,412);
        }
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 退出书圈
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/exit")
    public MyResult exitDiscussion(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("bdId"))){
            int userId = TokenUtils.getId(request.getHeader("authorization"));
            int discussionId = Integer.parseInt(request.getParameter("bdId"));
            result = bdUserService.exitDiscussion(userId,discussionId);
        }else {
            result = MyResult.failed("参数错误",null,412);
        }
        response.setStatus(result.getCode());
        return result;
    }
    /**
     * 获取圈友列表
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/queryuser")
    public MyResult queryDiscussionUser(HttpServletResponse response,HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("limit")) == true && IsTrueUtils.isTrue(request.getParameter("bdId")) == true){
            int limit = Integer.parseInt(request.getParameter("limit"));
            int discussionId = Integer.parseInt(request.getParameter("bdId"));
                if (limit == 0){
                    result = bdUserService.queryPopulation(discussionId);
                }else {
                    result = bdUserService.queryPopulationLimit(discussionId,limit);
                }
        }else {
            result = MyResult.failed("参数错误",null,412);
        }
        response.setStatus(result.getCode());
        return result;
    }
}
