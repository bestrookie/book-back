package com.bestrookie.service.collection;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.CollectionPojo;

/**
 * @author : bestrookie
 * @date : 20:28 2020/11/10
 */
public interface CollectionService {
    /**
     * 添加收藏
     * @param collectionPojo 收藏信息
     * @return 自定义返回类型
     */
    MyResult addCollection(CollectionPojo collectionPojo);

    /**
     * 取消收藏
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    MyResult cancelCollection(int userId,int bookId);

    /**
     * 是否收藏
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 是否收藏
     */
    boolean isCollection(int userId,int bookId);

}
