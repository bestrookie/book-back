package com.bestrookie.service.books;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookPojo;

/**
 * @author : bestrookie
 * @date : 19:17 2020/11/7
 */
public interface BookService {
    /**
     * 上传书籍
     *
     * @param bookPojo 书籍信息实体
     * @return 自定义返回类型
     */
    MyResult uploadBook(BookPojo bookPojo);
    /**
     * 查询pdf是否存在
     *
     * @param identity 身份码
     * @return 自定义返回类型
     */
    MyResult queryPdfExists(String identity);
    /**
     * 根据书的身份码修改书的状态
     * @param identity 身份码
     * @param state 状态
     */
    void updateBookState(String identity, boolean state);
    /**
     * 根据书的状态码修改书的价格
     * @param identity 身份码
     * @param price 价格
     */
    void updateBookPriceByIdentity(String identity, int price);
    /**
     * 根据d查询书籍信息
     * @param bookId 书籍id
     * @param userId 用户id
     * @return 自定义返回类型
     */
    MyResult queryBookById(int bookId,int userId);

    /**
     * 上传历史
     * @param userId 用户id
     * @param param 分页参数
     * @return 分页结果
     */
    PageResult queryMyUpload(PageRequestParam param, int userId);
}
