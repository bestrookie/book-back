package com.bestrookie.service.bookreview;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookReviewPojo;

/**
 * @author : bestrookie
 * @date : 9:53 2020/11/10
 */
public interface BookReviewService {
    /**
     * 发布评论
     * @param bookReviewPojo 评论信息
     * @return 自定返回类型
     */
    MyResult postComment(BookReviewPojo bookReviewPojo);
    /**
     * 分页查询书籍的所有评论
     * @param param 分页参数
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    PageResult queryAllBookReview(PageRequestParam param,int bookId);
    /**
     * 是否评论过此书籍
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 是否评论
     */
    boolean isReview(int userId,int bookId);

    /**
     * 计算书籍的总评分
     * @param bookId 书籍id
     * @return 总评分
     */
    double countBookValue(int bookId);
}
