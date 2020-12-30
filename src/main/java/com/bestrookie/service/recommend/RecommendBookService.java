package com.bestrookie.service.recommend;

import com.bestrookie.model.MyResult;

/**
 * @author : bestrookie
 * @date : 15:51 2020/12/28
 */
public interface RecommendBookService {
    /**
     * 推荐书籍
     * @param bookId 书籍id
     * @param nowUser 用户id
     * @return 自定义返回类型
     */
    MyResult recommendBook(int bookId,int nowUser);
}
