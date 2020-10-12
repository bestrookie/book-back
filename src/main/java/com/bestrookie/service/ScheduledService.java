package com.bestrookie.service;

import com.bestrookie.model.MyResult;
import com.bestrookie.service.attendance.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author : bestrookie
 * @date : 10:56 2020/10/12
 */
@Slf4j
@Service
public class ScheduledService {
    @Autowired
    private AttendanceService attendanceService;
    /**
     * 定时格式化签到表
     */
    @Scheduled(cron = "0 0 0 ? * MON")
    public void deleteAllAttendances(){
        MyResult result = attendanceService.deleteAllAttendance();
        log.info(result.getMsg());
    }
}
