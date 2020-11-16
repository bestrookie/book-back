package com.bestrookie.mapper;

import com.bestrookie.pojo.BookLimitPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 14:36 2020/11/15
 */
@Mapper
@Repository
public interface BookLimitMapper {
    /**
     * 添加书籍权限信息
     * @param bookLimitPojo 书籍权限信息
     * @return 是否添加成功
     */
    boolean addBookLimitInfo(BookLimitPojo bookLimitPojo);
    /**
     * 判断是否拥有权限
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 存在数量
     */
    int isExistBookLimit(@Param(value = "userId") int userId,@Param(value = "bookId") int bookId);
    /**
     * 修改用户书籍权限
     * @param bookLimitPojo 用户权限信息
     * @return 是否修改成功
     */
    boolean updateBookLimitInfo(BookLimitPojo bookLimitPojo);
    /**
     * 根据id查询书籍权限
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 书籍信息
     */
    BookLimitPojo queryLimitById(@Param(value = "userId") int userId,@Param(value = "bookId") int bookId);
    /**
     * 全本解锁
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 是否修改成功
     */
    boolean updateBookLimitAll(@Param(value = "userId") int userId,@Param(value = "bookId") int bookId);
    /**
     * 查询是否全本解锁
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    int isAllLimit(@Param(value = "userId") int userId,@Param(value = "bookId") int bookId);
    /**
     * 删除权限表
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 是否成功
     */
    boolean deleteLimit(@Param(value = "userId") int userId,@Param(value = "bookId") int bookId);
    /**
     * 查看解锁记录
     * @param userId 用户id
     * @return 解锁信息列表
     */
    List<BookLimitPojo> bookLimitHistory(@Param(value = "userId") int userId);
}
