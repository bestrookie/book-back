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
     * 分页查询封禁信息
     * @param param 分页参数
     * @param type 查询类型
     * @return 分页查询结果
     */
    PageResult queryUserBannedInfo(PageRequestParam param,int type);

    /**
     * 根据用户id禁言
     * @param bannedPojo 禁言信息实体
     * @return 自定义返回类型
     */
    MyResult bannedUserById(UserBannedPojo bannedPojo);

    /**
     * 解除禁言
     * @param bannedId 禁言信息id
     * @param rDate 解禁时间
     * @return 自定义返回类型
     */
    MyResult removeBannedById(int bannedId, long rDate);

    /**
     * 查询用户是否禁言
     * @param userId 用户id
     * @return 是否禁言
     */
    boolean isUserBanned(int userId);
}
