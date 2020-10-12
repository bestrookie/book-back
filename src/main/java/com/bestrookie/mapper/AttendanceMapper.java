package com.bestrookie.mapper;

import com.bestrookie.pojo.AttendancePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : bestrookie
 * @date : 16:18 2020/10/10
 */
@Mapper
public interface AttendanceMapper {
    /**
     * 添加一个用户签到
     * @param attendancePojo
     * @return
     */
    boolean addAttendance(AttendancePojo attendancePojo);
    /**
     * 根据用户id查询签到信息
     * @param userId
     * @return
     */
    AttendancePojo queryAttendanceById(int userId);
    /**
     * 修改签到信息
     * @param attendancePojo
     * @return
     */
    boolean updateAttendance(@Param("attendancePojo") AttendancePojo attendancePojo);
    /**
     * 格式化签到表
     * @return
     */
    boolean deleteAllAttendance();
}
