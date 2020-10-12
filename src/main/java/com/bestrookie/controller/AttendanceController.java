package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.AttendancePojo;
import com.bestrookie.service.attendance.AttendanceService;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 19:13 2020/10/10
 */
@RestController
@RequestMapping("api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    /**
     * 获取签到信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/getattendance")
    public MyResult getAttendanceInfo(HttpServletRequest request, HttpServletResponse response){
        int userId = TokenUtils.getId(request.getHeader("authorization"));
        MyResult myResult = attendanceService.getUserAttendanceIfo(userId);
        response.setStatus(myResult.getCode());
        return attendanceService.getAttendanceMainInfo((AttendancePojo) myResult.getObj());
    }
    /**
     *修改签到信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/attendance")
    public MyResult updateAttendance(HttpServletRequest request,HttpServletResponse response){
        int userId = TokenUtils.getId(request.getHeader("authorization"));
        String phone = TokenUtils.getInfo(request.getHeader("authorization"));
        MyResult myResult = attendanceService.updateAttendanceInfo(userId,phone);
        response.setStatus(myResult.getCode());
        return myResult;
    }
}