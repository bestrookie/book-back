package com.bestrookie.service.dynamicReview;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.DynamicReviewPojo;

/**
 * @author : bestrookie
 * @date : 15:46 2020/10/19
 */
public interface DynamicReviewService {
    /**
     * 查询动态信息
     * @param param 分页参数
     * @param dynamicId 动态id
     * @return 自定义返回类型
     */
    PageResult queryDynamicReview(PageRequestParam param, int dynamicId);
    /**
     * 删除评论
     * @param dynamicId 动态id
     * @param reviewId 评论id
     * @param userId 用户id
     * @return 自定义返回类型
     */
    MyResult deleteDynamicReview(int dynamicId,int reviewId,int userId);
    /**
     *发布评论
     * @param dynamicReviewPojo 评论信息
     * @return 自定义返回类型
     */
    MyResult releaseDynamicReview(DynamicReviewPojo dynamicReviewPojo);
    /**
     * 获取评论数量
     * @param dynamicId 动态id
     * @return 数量
     */
    int reviewCount(int dynamicId);
}
