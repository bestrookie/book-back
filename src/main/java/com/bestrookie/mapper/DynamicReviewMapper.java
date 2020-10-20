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
     * @param dynamicId
     * @return
     */
    List<DynamicReviewPojo> queryDynamicReviews(@Param(value = "dynamicId") int dynamicId);
    /**
     * 查询子评论
     * @param dynamicId
     * @param parentId
     * @return
     */
    List<DynamicReviewPojo> querySonReviews(@Param(value = "dynamicId") int dynamicId,@Param(value = "parentId") int parentId);
    /**
     * 查询评论数量
     * @param dynamicId
     * @return
     */
    int reviewCount(@Param(value = "dynamicId") int dynamicId);
    /**
     * 根据用户id删除评论
     * @param reviewId
     * @param userId
     * @return
     */
    boolean deleteReviewById(@Param(value = "reviewId") int reviewId,@Param(value = "userId") int userId);
    /**
     * 删除评论
     * @param reviewId
     * @return
     */
    boolean deleteReview(@Param(value = "reviewId")int reviewId);
    /**
     * 添加评论
     * @param dynamicReviewPojo
     * @return
     */
    boolean addReview(@Param(value = "dynamicReviewPojo") DynamicReviewPojo dynamicReviewPojo);

}
