package com.bestrookie.controller;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.GiveLikePojo;
import com.bestrookie.service.givelike.GiveLikeService;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 9:04 2020/10/17
 */
@RestController
@RequestMapping("api/givelike")
public class GiveLikeController {
    @Autowired
    private GiveLikeService giveLikeService;

    /**
     * 点赞
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/givelike")
    MyResult giveLike(HttpServletResponse response, HttpServletRequest request){
        GiveLikePojo giveLikePojo = new GiveLikePojo();
        MyResult result = null;
        giveLikePojo.setDynamicId(Integer.parseInt(request.getParameter("dynamicId")));
        giveLikePojo.setLikeDate(System.currentTimeMillis());
        giveLikePojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
        if (giveLikePojo == null){
            result = MyResult.failed("传入参数为空",null,410);
        }else {
            result = giveLikeService.giveLike(giveLikePojo);
        }
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 取消点赞
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/cancellike")
    MyResult cancelLiked(HttpServletRequest request,HttpServletResponse response){
        MyResult result = null;
        if (Integer.parseInt(request.getParameter("dynamicId")) > 0 && TokenUtils.getId(request.getHeader("authorization")) > 0){
            result = giveLikeService.cancelLiked( Integer.parseInt(request.getParameter("dynamicId")),TokenUtils.getId(request.getHeader("authorization")));
        }else {
            result = MyResult.failed("传入参数错误",null,410);
        }
        response.setStatus(result.getCode());
        return  result;
    }
}
