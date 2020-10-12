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
     * @param userId
     * @return
     */
    MyResult getUserAttendanceIfo(int userId);
    /**
     * 过滤签到信息
     * @param attendancePojo
     * @return
     */
    MyResult getAttendanceMainInfo(AttendancePojo attendancePojo);
    /**
     * 修改签到信息
     * @param userId
     * @return
     */
    MyResult updateAttendanceInfo(int userId,String phone);
    /**
     * 格式化签到表
     * @return
     */
    MyResult deleteAllAttendance();
}
