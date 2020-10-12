package com.bestrookie.utils;

import com.bestrookie.pojo.AttendancePojo;

/**
 * @author : bestrookie
 * @date : 19:05 2020/10/10
 */
public class InitAttendanceUtils {
    public static AttendancePojo initAttendance(int userId){
        AttendancePojo attendancePojo = new AttendancePojo();
        attendancePojo.setUserId(userId);
        attendancePojo.setAttendanceTimes(0);
        return  attendancePojo;
    }
}
