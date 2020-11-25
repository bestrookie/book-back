package com.bestrookie.service.attendance;

import com.bestrookie.mapper.AttendanceMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.AttendancePojo;
import com.bestrookie.pojo.UserPojo;
import com.bestrookie.service.user.UserService;
import com.bestrookie.utils.InitAttendanceUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
/**
 * @author : bestrookie
 * @date : 19:02 2020/10/10
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private UserService userService;
    private static final int DAY_1 = 100;
    private static final int DAY_2 = 200;
    private static final int DAY_3 = 400;
    private static final int DAY_4 = 400;
    private static final int DAY_5 = 500;
    private static final int DAY_6 = 500;
    private static final int DAY_7 = 1000;
    /**
     * 查询签到信息
     * @param userId 用户id
     * @return 自定义返回类型
     */
    @Override
    public MyResult getUserAttendanceInfo(int userId) {
        AttendancePojo attendancePojo = attendanceMapper.queryAttendanceById(userId);
        if (attendancePojo != null){
            return MyResult.success(attendancePojo,"签到信息");
        }else {
            attendancePojo = InitAttendanceUtils.initAttendance(userId);
            boolean flg = attendanceMapper.addAttendance(attendancePojo);
            if (flg){
                return MyResult.success(attendancePojo,"成功生成签到表");
            }
            return MyResult.failed("查询失败",null,407);
        }
    }
    /**
     * 过滤签到信息
     * @param attendancePojo 签到信息实体
     * @return 自定义返回结果集
     */
    @Override
    public MyResult getAttendanceMainInfo(AttendancePojo attendancePojo) {
        int attendanceTimes = attendancePojo.getAttendanceTimes();
        long attendanceDate = attendancePojo.getAttendanceDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(attendanceDate));
        return MyResult.success(Map.of("attendanceDate", DateUtils.isSameDay(Calendar.getInstance(), calendar), "attendanceTimes", attendanceTimes));
    }

    /**
     * 签到
     * @param userId 用户id
     * @param phone 用户手机号
     * @return 自定义返回结果集
     */
    @Override
    public MyResult updateAttendanceInfo(int userId,String phone) {
        AttendancePojo attendancePojo = attendanceMapper.queryAttendanceById(userId);
        UserPojo userInfo = (UserPojo) userService.getUserInfo(phone).getObj();
        attendancePojo.setAttendanceDate(System.currentTimeMillis());
        attendancePojo.setAttendanceTimes(attendancePojo.getAttendanceTimes()+1);
        if (attendanceMapper.updateAttendance(attendancePojo)){
            try {
                switch (attendancePojo.getAttendanceTimes()) {
                    case 1:
                        userInfo.setUserCoin(userInfo.getUserCoin()+DAY_1);
                        userService.updateUserCoin(userInfo.getUserCoin(),phone);
                        break;
                    case 2:
                        userInfo.setUserCoin(userInfo.getUserCoin()+DAY_2);
                        userService.updateUserCoin(userInfo.getUserCoin(),phone);
                        break;
                    case 3:
                        userInfo.setUserCoin(userInfo.getUserCoin()+DAY_3);
                        userService.updateUserCoin(userInfo.getUserCoin(),phone);
                        break;
                    case 4:
                        userInfo.setUserCoin(userInfo.getUserCoin()+DAY_4);
                        userService.updateUserCoin(userInfo.getUserCoin(),phone);
                        break;
                    case 5:
                        userInfo.setUserCoin(userInfo.getUserCoin()+DAY_5);
                        userService.updateUserCoin(userInfo.getUserCoin(),phone);
                        break;
                    case 6:
                        userInfo.setUserCoin(userInfo.getUserCoin()+DAY_6);
                        userService.updateUserCoin(userInfo.getUserCoin(),phone);
                        break;
                    case 7:
                        userInfo.setUserCoin(userInfo.getUserCoin()+DAY_7);
                        userService.updateUserCoin(userInfo.getUserCoin(),phone);
                        break;
                    default:
                        break;
                }
                return getAttendanceMainInfo(attendancePojo);
            }catch (Exception e){
                return MyResult.failed("签到失败",false,507);
            }


        }else {
            return MyResult.failed("签到失败",false,507);
        }
    }
    /**
     * 格式化签到表
     * @return 自定义结果集
     */
    @Override
    public MyResult deleteAllAttendance() {
        if (attendanceMapper.deleteAllAttendance()){
            return MyResult.success(true,"签到信息已经重置");
        }else {
            return MyResult.failed("重置签到信息失败",false,507);
        }
    }
}
