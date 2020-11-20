package com.bestrookie.service.booklimit;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookLimitPojo;

/**
 * @author : bestrookie
 * @date : 19:33 2020/11/15
 */
public interface  BookLimitService {
    /**
     * 获取书籍部分权限
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    MyResult getBookLimit(int userId,int bookId);
    /**
     * 判断用户是否拥有权限
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    boolean isExistBookLimit(int userId,int bookId);
    /**
     * 全本解锁
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    MyResult getBookAllLimit(int userId,int bookId);
    /**
     * 查询书籍权限
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    MyResult queryBookLimit(int userId,int bookId);
    /**
     * 查询是否全本解锁
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 是否存在
     */
    boolean isAllLimit(int userId,int bookId);

    /**
     * 第一次解锁
     * @param userId 用户id
     * @param bookId 书籍id
     * @param type 类型
     * @return 自定义返回类型
     */
    MyResult addLimit(int userId,int bookId,int type);

    /**
     * 查看解锁历史
     * @param userId 用户id
     * @param param 分页参数
     * @return 分页结果
     */
    PageResult queryBookLimitHistory(PageRequestParam param, int userId);

    /**
     * 查询权限信息
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 权限信息
     */
    BookLimitPojo queryById(int userId,int bookId);

    /**
     * 解锁部分后全部解锁
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    MyResult partAllLimit(int userId,int bookId);
}
