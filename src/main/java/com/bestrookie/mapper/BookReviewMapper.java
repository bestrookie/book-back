package com.bestrookie.mapper;

import com.bestrookie.pojo.BookReviewPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 9:28 2020/11/10
 */
@Mapper
@Repository
public interface BookReviewMapper {
    /**
     * 添加评论
     * @param bookReviewPojo 评论信息
     * @return 是否添加成功
     */
    boolean addBookReview(BookReviewPojo bookReviewPojo);
    /**
     * 是否发表评论
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 评论数量
     */
    int isReview(@Param(value = "userId") int userId, @Param(value = "bookId") int bookId);
    /**
     * 查看书籍的评论数量
     * @param bookId 书籍id
     * @return 评论数量
     */
    int reviewCount(@Param(value = "bookId") int bookId);
    /**
     * 查看书籍的所有评论
     * @param bookId 书籍id
     * @return 所有的评论
     */
    List<BookReviewPojo> queryAllReviews(@Param(value = "bookId") int bookId);
}
