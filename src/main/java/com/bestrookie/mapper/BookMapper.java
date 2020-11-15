package com.bestrookie.mapper;

import com.bestrookie.pojo.BookPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 18:54 2020/11/7
 */
@Repository
@Mapper
public interface BookMapper {
    /**
     * 添加书籍
     *
     * @param book 书籍信息实体
     * @return 是否添加成功
     */
    boolean addBookMapper(BookPojo book);

    /**
     * 查询pdf是否存在
     *
     * @param identity 身份码
     * @return 存在的数量
     */
    int queryExistsIdentity(@Param(value = "identity") String identity);

    /**
     * 修改书的状态
     *
     * @param identity 身份码
     * @param state    状态
     * @return 自定义返回类型
     */
    boolean updateBookState(@Param(value = "identity") String identity, @Param(value = "state") boolean state);

    /**
     * 根据状态码修改价格
     *
     * @param identity 身份码
     * @param price    价格
     * @return 是否修改成功
     */
    boolean updateBookPriceByIdentity(@Param(value = "identity") String identity, @Param(value = "price") int price);
    /**
     * 查询所有的书籍
     *
     * @return 书籍列表
     */
    List<BookPojo> queryAllBooks();
    /**
     * 根据书籍id查询书籍信息
     *
     * @param bookId 书籍id
     * @return 书籍信息实体
     */
    BookPojo queryBookById(@Param(value = "bookId") int bookId);
    /**
     * 修改点击量
     *
     * @param bookId     书籍id
     * @param clickCount 点击量
     */
    void updateBookSearch(@Param(value = "bookId") int bookId, @Param(value = "clickCount") int clickCount);
    /**
     * 我的上传
     *
     * @param userId 用户id
     * @return 书籍列表
     */
    List<BookPojo> queryMyUpload(@Param(value = "userId") int userId);
    /**
     * 查看用户收藏
     * @param userId 用户id
     * @return 书籍列表
     */
    List<BookPojo> queryMyCollection(@Param(value = "userId") int userId);
    /**
     * 根据类型查看书籍
     * @param typeId 书籍类型id
     * @return 书籍列表
     */
    List<BookPojo> queryAllByType(@Param(value = "typeId") int typeId);
    /**
     * 模糊查询
     * @param key 关键词
     * @return 书籍信息列表
     */
    List<BookPojo> queryFuzzy(@Param(value = "key") String key);
    /**
     * 模糊查询根据类型
     * @param key 关键词
     * @param typeId leixingid
     * @return 书籍列表
     */
    List<BookPojo> queryFuzzyByType(@Param(value = "key") String key,@Param(value = "typeId") int typeId);
    /**
     * 根据id设置状态
     * @param bookId 书籍id
     * @param state 状态
     * @return 是否成功
     */
    boolean updateStateById(@Param(value = "bookId") int bookId,@Param("state") boolean state);
    /**
     * 修改书籍信息
     * @param bookPojo 书籍信息实体
     * @return 是否修改成功
     */
    boolean updateBookInfo(BookPojo bookPojo);

    /**
     * 书籍同类中的排名
     * @param typeId 书籍id
     * @return 书籍信息
     */
    List<BookPojo>booksAreRankByType(@Param(value = "typeId") int typeId);

    /**
     * 查询为过审书籍
     * @return 书籍列表
     */
    List<BookPojo>queryUnBook();
    /**
     * 书籍排行
     * @return 书籍列表
     */
    List<BookPojo>bookTop();

    /**
     * 收藏数加一
     * @param bookId 书籍id
     * @return 是否成功
     */
    boolean addCollection(@Param(value = "bookId") int bookId);

    /**
     * 收藏数量减一
     * @param bookId 书籍id
     * @return 是否成功
     */
    boolean reduceCollection(@Param(value = "bookId") int bookId);

}
