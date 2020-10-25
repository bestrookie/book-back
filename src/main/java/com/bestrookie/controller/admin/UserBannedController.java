package com.bestrookie.controller.admin;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.BannedParam;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.UserBannedPojo;
import com.bestrookie.service.userbanned.UserBannedService;
import com.bestrookie.utils.IsTrueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 18:47 2020/10/23
 */
@RestController
@RequestMapping("/api/admin/banned")
public class UserBannedController {
    private final static long ONE_DAY = 86400000L;
    private final static long ONE_WEEK = 604800000L;
    private final static long HALF_MONTH = 1296000000L;
    private final static long ONE_MONTH = 2592000000L;
    @Autowired
    private UserBannedService userBannedService;

    /**
     * 查询所有禁言用户
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/querybanned")
    public MyResult queryUserBanned(HttpServletResponse response, HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))){
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = userBannedService.queryUserBannedInfo(param);
            if (pageResult == null){
                result = MyResult.failed("查询封禁列表失败",null,513);
            }else {
                result = MyResult.success(pageResult);
            }
        }else {
            result = MyResult.failed("参数错误",null,412);
        }
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 根据用户id封禁用户
     * @param response
     * @param param
     * @return
     */
    @PostMapping("/banneduser")
    public MyResult BannedUserById(HttpServletResponse response,@RequestBody BannedParam param){
        MyResult result;
        UserBannedPojo userBannedPojo = new UserBannedPojo();
        userBannedPojo.setUserId(param.getUserId());
        userBannedPojo.setBannedDes(param.getBannedDes());
        userBannedPojo.setBannedDate(System.currentTimeMillis());
        if (param.isCustom()){
            userBannedPojo.setRemoveDate(System.currentTimeMillis()+param.getTime());
        }else {
            switch (param.getBannedType()){
                case 1:
                    userBannedPojo.setRemoveDate(System.currentTimeMillis()+ONE_DAY);
                    break;
                case 2:
                    userBannedPojo.setRemoveDate(System.currentTimeMillis()+ONE_WEEK);
                    break;
                case 3:
                    userBannedPojo.setRemoveDate(System.currentTimeMillis()+HALF_MONTH);
                    break;
                case 4:
                    userBannedPojo.setRemoveDate(System.currentTimeMillis()+ONE_MONTH);
                    break;
                default:
                    break;
            }
        }
        result = userBannedService.bannedUserById(userBannedPojo);
        response.setStatus(result.getCode());
        return  result;
    }
    @GetMapping("/removebanned")
    public MyResult RemoveBannedById(HttpServletRequest request,HttpServletResponse response){
        Integer.parseInt(request.getParameter("bannedId"));
        MyResult result = userBannedService.removeBannedById(Integer.parseInt(request.getParameter("bannedId")), System.currentTimeMillis());
        response.setStatus(result.getCode());
        return result;
    }

}
