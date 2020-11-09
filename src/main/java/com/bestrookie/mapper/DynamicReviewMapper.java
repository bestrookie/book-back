package com.bestrookie.mapper;


import com.bestrookie.pojo.DynamicReviewPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 16:00 2020/10/19
 */
@Mapper
@Repository
public interface DynamicReviewMapper {
    /**
     * 查询某一条数据的所有动态
     * @param dynamicId 动态id
     * @return 动态列表
     */
    List<DynamicReviewPojo> queryDynamicReviews(@Param(value = "dynamicId") int dynamicId);
    /**
     * 查询子评论
     * @param dynamicId 子评论id
     * @param parentId 父评论id
     * @return 评论列表
     */
    List<DynamicReviewPojo> querySonReviews(@Param(value = "dynamicId") int dynamicId,@Param(value = "parentId") int parentId);
    /**
     * 查询评论数量
     * @param dynamicId 动态id
     * @return 数量
     */
    int reviewCount(@Param(value = "dynamicId") int dynamicId);
    /**
     * 根据用户id删除评论
     * @param reviewId 评论id
     * @param userId 用户id
     * @return 是否成功
     */
    boolean deleteReviewById(@Param(value = "reviewId") int reviewId,@Param(value = "userId") int userId);
    /**
     * 删除评论
     * @param reviewId 评论id
     * @return 是否成功
     */
    boolean deleteReview(@Param(value = "reviewId")int reviewId);
    /**
     * 添加评论
     * @param dynamicReviewPojo 评论信息实体
     * @return 是否成功
     */
    boolean addReview(@Param(value = "dynamicReviewPojo") DynamicReviewPojo dynamicReviewPojo);

}
