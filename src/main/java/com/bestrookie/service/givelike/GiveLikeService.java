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
     * @param giveLikePojo
     * @return
     */
    MyResult giveLike(GiveLikePojo giveLikePojo);
    /**
     * 获取点赞数
     * @param dynamicId
     * @return
     */
    int giveLikeNum(int dynamicId);
    /**
     * 查看是否点赞
     * @param dynamicId
     * @param userId
     * @return
     */
    boolean isLiked(int dynamicId,int userId);
    /**
     * 取消点赞
     * @param dynamicId
     * @param userId
     * @return
     */
    MyResult cancelLiked(int dynamicId,int userId);
}
