package com.bestrookie.controller.admin;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.LoginUser;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.service.admin.AdminService;
import com.bestrookie.service.user.UserService;
import com.bestrookie.utils.IsTrueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 18:54 2020/10/21
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    /**
     * 管理员登录
     * @param login
     * @param response
     * @return
     */
    @PostMapping("/login")
    public MyResult adminLogin(@RequestBody LoginUser login, HttpServletResponse response) {
        MyResult result;
        if (login != null) {
            result = adminService.adminLogin(login.getPhone(), login.getPassword());
        } else {
            result = MyResult.failed("账号密码不能为空");
        }
        response.setStatus(result.getCode());
        return result;
    }
    @GetMapping("/queryalluser")
    public MyResult queryAllUser(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))){
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = userService.queryAllUsers(param);
            if (pageResult == null){
                result = MyResult.failed("查询用户列表失败",null,506);
            }else {
                result = MyResult.success(pageResult);
            }
        }else {
            result = MyResult.failed("参数错误",null,412);
        }
        response.setStatus(result.getCode());
        return result;
    }
}
