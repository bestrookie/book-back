package com.bestrookie.service.userbanned;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.UserBannedPojo;

/**
 * @author : bestrookie
 * @date : 10:29 2020/10/22
 */
public interface UserBannedService {
    /**
     * 分页查询用户封禁信息
     * @param param
     * @return
     */
    PageResult queryUserBannedInfo(PageRequestParam param);

    /**
     * 根据用户id禁言
     * @param bannedPojo
     * @return
     */
    MyResult bannedUserById(UserBannedPojo bannedPojo);

    /**
     * 解除禁言
     * @param bannedId
     * @param rDate
     * @return
     */
    MyResult removeBannedById(int bannedId, long rDate);

    /**
     * 查询用户是否禁言
     * @param userId
     * @return
     */
    boolean isUserBanned(int userId);
}
