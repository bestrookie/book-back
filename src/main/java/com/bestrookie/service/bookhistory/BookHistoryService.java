package com.bestrookie.service.bookhistory;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookHistoryPojo;

/**
 * @author : bestrookie
 * @date : 20:22 2020/11/17
 */
public interface BookHistoryService {
    /**
     * 是否存在历史记录
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 是否存在
     */
    boolean isHistory(int userId,int bookId);

    /**
     * 添加历史记录
     * @param historyPojo 记录信息
     * @return 自定义返回类型
     */
    MyResult addHistory(BookHistoryPojo historyPojo);

    /**
     * 查看记录
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    MyResult queryHistory(int userId,int bookId);

    /**
     * 查看所有类型
     * @param param 分页参数
     * @param userId 自定义返回类型
     * @return 自定义返回了类型
     */
    PageResult queryAllHistory(PageRequestParam param,int userId);

    /**
     * 修改历史
     * @param historyPojo 历史信息
     * @return 自定义返回类型
     */
    MyResult updateHistory(BookHistoryPojo historyPojo);
}
