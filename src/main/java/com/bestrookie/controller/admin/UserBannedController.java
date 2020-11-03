package com.bestrookie.controller.admin;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.BannedParam;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.LogPojo;
import com.bestrookie.pojo.UserBannedPojo;
import com.bestrookie.service.adminlog.AdminLogService;
import com.bestrookie.service.userbanned.UserBannedService;
import com.bestrookie.utils.InitLogInfoUtils;
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
    @Autowired
    private AdminLogService logService;

    /**
     * 查询所有禁言用户
     *
     * @param response 响应参数
     * @param request 请求参数
     * @return 自定义返回类型
     */
    @GetMapping("/querybanned")
    public MyResult queryUserBanned(HttpServletResponse response, HttpServletRequest request) {
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = userBannedService.queryUserBannedInfo(param, Integer.parseInt(request.getParameter("type")));
            if (pageResult == null) {
                result = MyResult.failed("查询封禁列表失败", null, 513);
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
     * 根据用户id封禁用户
     *
     * @param response 响应参数
     * @param param 分页信息
     * @return 自定结果集
     */
    @PostMapping("/banneduser")
    public MyResult bannedUserById(HttpServletResponse response, @RequestBody BannedParam param,HttpServletRequest request) {
        MyResult result;
        UserBannedPojo userBannedPojo = new UserBannedPojo();
        userBannedPojo.setUserId(param.getUserId());
        userBannedPojo.setBannedDes(param.getBannedDes());
        userBannedPojo.setBannedDate(System.currentTimeMillis());
        String desBanned = "对用户"+param.getUserId()+"进行禁言操作";
        if (param.isCustom()) {
            userBannedPojo.setRemoveDate(System.currentTimeMillis() + param.getTime());
        } else {
            switch (param.getBannedType()) {
                case 1:
                    userBannedPojo.setRemoveDate(System.currentTimeMillis() + ONE_DAY);
                    break;
                case 2:
                    userBannedPojo.setRemoveDate(System.currentTimeMillis() + ONE_WEEK);
                    break;
                case 3:
                    userBannedPojo.setRemoveDate(System.currentTimeMillis() + HALF_MONTH);
                    break;
                case 4:
                    userBannedPojo.setRemoveDate(System.currentTimeMillis() + ONE_MONTH);
                    break;
                default:
                    break;
            }
        }
        LogPojo log = InitLogInfoUtils.initLogInfo(request,desBanned);
        logService.addAdminLog(log);
        result = userBannedService.bannedUserById(userBannedPojo);
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 解除禁言
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义结果集
     */
    @GetMapping("/removebanned")
    public MyResult removeBannedById(HttpServletRequest request, HttpServletResponse response) {
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("bannedId"))){
            String desBanned = "对封禁记录"+request.getParameter("bannedId")+"进行解除禁言操作";
            LogPojo logPojo = InitLogInfoUtils.initLogInfo(request,desBanned);
            logService.addAdminLog(logPojo);
            result = userBannedService.removeBannedById(Integer.parseInt(request.getParameter("bannedId")), System.currentTimeMillis());
        }else {
            result = MyResult.failed("参数错误",false,412);
        }
        response.setStatus(result.getCode());
        return result;
    }

}
