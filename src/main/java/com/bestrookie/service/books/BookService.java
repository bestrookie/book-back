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
    /**
     * 查看用户收藏
     * @param userId 用户id
     * @return 分页结果
     */
    MyResult queryMyCollect(int userId);
    /**
     * 模糊查询
     * @param key 关键词
     * @param param 分页参数
     * @param typeId 类型id
     * @return 自定义返回类型
     */
    PageResult queryFuzzy(PageRequestParam param,String key,int typeId);
    /**
     * 全局搜索中的搜索id
     * @param param 分页信息
     * @param bookId 书籍id
     * @return 分页结果
     */
    PageResult queryById(PageRequestParam param,int bookId);
    /**
     * 管理员修改书籍状态
     * @param bookId 书籍id
     * @param status 状态
     * @return 自定义返回类型
     */
    MyResult updateStatus(int bookId,boolean status);
    /**
     * 修改书籍信息
     * @param bookPojo 书籍信息
     * @return 自定义返回类型
     */
    MyResult updateBookInfo(BookPojo bookPojo);

    /**
     * 根据类型查询点击量排名
     * @param typeId 类型id
     * @return 自定义返回类型
     */
    MyResult booksOutByType(int typeId);

    /**
     * 查看未过深书籍
     * @param param 分页参数
     * @return 分页结果
     */
    PageResult queryUnBook(PageRequestParam param);

    /**
     * 查询此书是否过审
     * @param bookId 书籍id
     * @return 是否过审
     */
    boolean isBookTrue(int bookId);
    /**
     * 查看书籍排行版
     * @return 自定义返回类型
     */
    MyResult queryTop();
    /**
     * 收藏数加一
     * @param bookId 书籍id
     * @return 是否成功
     */
    boolean addCollection(int bookId);

    /**
     * 收藏数量减一
     * @param bookId 书籍id
     * @return 是否成功
     */
    boolean reduceCollection(int bookId);

    /**
     * 添加书籍页数
     * @param bookId 书籍id
     * @param pageCount 页数
     * @return 是否添加成功
     */
    boolean addBookPage(int bookId,int pageCount);

    /**
     * 根据id查找书籍
     * @param bookId 书籍id
     * @return 书籍信息
     */
    BookPojo queryBookById(int bookId);
}
