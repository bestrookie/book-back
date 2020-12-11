package com.bestrookie.service.givelike;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.GiveLikePojo;

/**
 * @author : bestrookie
 * @date : 20:51 2020/10/16
 */
public interface GiveLikeService {
    /**
     * 添加点赞信息
     * @param giveLikePojo 点赞信息
     * @return 自定义返回类型
     */
    MyResult giveLike(GiveLikePojo giveLikePojo);
    /**
     * 获取点赞数
     * @param dynamicId 动态id
     * @return 数量
     */
    int giveLikeNum(int dynamicId);
    /**
     * 查看是否点赞
     * @param dynamicId 动态id
     * @param userId 用户id
     * @return 自定义返回类型
     */
    boolean isLiked(int dynamicId,int userId);
    /**
     * 取消点赞
     * @param dynamicId 动态id
     * @param userId 用户id
     * @return 自定义返回类型
     */
    MyResult cancelLiked(int dynamicId,int userId);
}
