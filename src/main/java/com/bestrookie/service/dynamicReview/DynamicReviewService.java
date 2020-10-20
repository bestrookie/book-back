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
     * @param param
     * @param dynamicId
     * @return
     */
    PageResult queryDynamicReview(PageRequestParam param, int dynamicId);
    /**
     * 删除评论
     * @param dynamicId
     * @param reviewId
     * @param userId
     * @return
     */
    MyResult deleteDynamicReview(int dynamicId,int reviewId,int userId);
    /**
     *发布评论
     * @param dynamicReviewPojo
     * @return
     */
    MyResult releaseDynamicReview(DynamicReviewPojo dynamicReviewPojo);
    /**
     * 获取评论数量
     * @param dynamicId
     * @return
     */
    int reviewCount(int dynamicId);
}
