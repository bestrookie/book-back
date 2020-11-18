package com.bestrookie.mapper;

import com.bestrookie.pojo.BookHistoryPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 19:43 2020/11/17
 */
@Mapper
@Repository
public interface BookHistoryMapper {
    /**
     * 填加历史记录
     * @param bookHistoryPojo 历史记录
     * @return 是否添加成功
     */
    boolean addBookHistory(BookHistoryPojo bookHistoryPojo);
    /**
     * 查看浏览记录
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 浏览记录
     */
    BookHistoryPojo queryBookHistory(@Param(value = "userId") int userId,@Param(value = "bookId") int bookId);

    /**
     * 查看一个人的所有记录
     * @param userId 用户id
     * @return 记录列表
     */
    List<BookHistoryPojo> queryBookHistoryById(@Param(value = "userId") int userId);

    /**
     * 修改历史记录
     * @param bookHistoryPojo 记录信息
     * @return 是否修改成功
     */
    boolean updateBookHistory(BookHistoryPojo bookHistoryPojo);

    /**
     * 是否存在记录
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 数量
     */
    int isHistory(@Param(value = "userId") int userId,@Param(value = "bookId") int bookId);

    /**
     * 查询所有记录
     * @return 记录列表
     */
    List<BookHistoryPojo> querAllHistory();
}
