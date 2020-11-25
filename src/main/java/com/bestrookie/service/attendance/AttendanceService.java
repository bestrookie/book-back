package com.bestrookie.service.attendance;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.AttendancePojo;

/**
 * @author : bestrookie
 * @date : 18:58 2020/10/10
 */
public interface AttendanceService {
    /**
     * 获取签到信息
     * @param userId 用户id
     * @return 自定义返回类型
     */
    MyResult getUserAttendanceInfo(int userId);
    /**
     * 过滤签到信息
     * @param attendancePojo 签到信息实体
     * @return 自定义返回类型
     */
    MyResult getAttendanceMainInfo(AttendancePojo attendancePojo);

    /**
     * 更新签到信息
     * @param userId 用户id
     * @param phone 用户手机号
     * @return 自定义返回类型
     */
    MyResult updateAttendanceInfo(int userId,String phone);
    /**
     * 格式化签到表
     * @return 自定义返回类型
     */
    MyResult deleteAllAttendance();
}
