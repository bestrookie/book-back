package com.bestrookie.service.usertop;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.UserTopPojo;

/**
 * @author : bestrookie
 * @date : 16:48 2020/11/14
 */
public interface UserTopService {
    /**
     * 是否存在排行信息
     * @param userId 用户id
     * @return 是否存在
     */
    boolean isTopExist(int userId);

    /**
     * 添加用户排行信息
     * @param userTopPojo 排行信息
     */
    void addUserTopInfo(UserTopPojo userTopPojo);

    /**
     *  更新用户排行信息
     * @param userId 用户id
     */
    void updateUserTop(int userId);

    /**
     * 查询用户排行信息
     * @return 自定义返回类型
     */
    MyResult queryUserTop();
}
