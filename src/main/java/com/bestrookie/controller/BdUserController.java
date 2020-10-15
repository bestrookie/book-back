package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.BdUserPojo;
import com.bestrookie.service.bduser.BdUserService;
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
        bdUser.setBdId(Integer.parseInt(request.getParameter("bdId")));
        bdUser.setBduDate(System.currentTimeMillis());
        bdUser.setUserId(TokenUtils.getId(request.getHeader("authorization")));
        MyResult result = bdUserService.joinDiscussion(bdUser);
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
        int userId = TokenUtils.getId(request.getHeader("authorization"));
        int discussionId = Integer.parseInt(request.getParameter("bdId"));
        MyResult result;
        if (userId>0 && discussionId > 0){
            result = bdUserService.exitDiscussion(userId,discussionId);
        }else {
            result = MyResult.failed("id不能为负数",null,408);
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
        int limit = Integer.parseInt(request.getParameter("limit"));
        int discussionId = Integer.parseInt(request.getParameter("bdId"));
        MyResult result;
        if (limit >= 0 || discussionId > 0){
            if (limit == 0){
                result = bdUserService.queryPopulation(discussionId);
            }else {
                result = bdUserService.queryPopulationLimit(discussionId,limit);
            }
        }else {
            result = MyResult.failed("参数错误",null,409);
        }
        response.setStatus(result.getCode());
        return result;
    }
}
