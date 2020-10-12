package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 16:25 2020/10/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendancePojo {
    private int attendanceId;
    private int userId;
    private long attendanceDate;
    private  int attendanceTimes = 0;

    public AttendancePojo(int userId, long attendanceDate, int attendanceTimes) {
        this.userId = userId;
        this.attendanceDate = attendanceDate;
        this.attendanceTimes = attendanceTimes;
    }
}
