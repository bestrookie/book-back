package com.bestrookie.mapper;

import com.bestrookie.pojo.CollectionPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author : bestrookie
 * @date : 20:16 2020/11/10
 */
@Mapper
@Repository
public interface CollectionMapper {
    /**
     * 添加收藏
     * @param collectionPojo 收藏信息
     * @return 是否添加成功
     */
    boolean addCollection(CollectionPojo collectionPojo);
    /**
     * 取消收藏
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 是否成功
     */
    boolean cancelCollection(@Param(value = "userId") int userId,@Param(value = "bookId") int bookId);
    /**
     * 是否收藏
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 收藏数量
     */
    int isCollection(@Param(value = "userId") int userId,@Param(value = "bookId") int bookId);
    /**
     * 查看一本书的收藏数量
     * @param bookId 书籍id
     * @return 数量
     */
    int collectCount(@Param(value = "bookId") int bookId);
}
