package com.bestrookie.mapper;

import com.bestrookie.pojo.BookPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author : bestrookie
 * @date : 18:54 2020/11/7
 */
@Repository
@Mapper
public interface BookMapper {
    /**
     * 添加书籍
     * @param book 书籍信息实体
     * @return 是否添加成功
     */
    boolean addBookMapper(BookPojo book);

    /**
     * 查询pdf是否存在
     * @param identity 身份码
     * @return 存在的数量
     */
    int queryExistsIdentity(@Param(value = "identity") String identity);

    /**
     * 修改书的状态
     * @param identity 身份码
     * @param state 状态
     * @return 自定义返回类型
     */
    boolean updateBookState(@Param(value = "identity") String identity,@Param(value = "state") boolean state);

    /**
     * 根据状态码修改价格
     * @param identity 身份码
     * @param price 价格
     * @return 是否修改成功
     */
    boolean updateBookPriceByIdentity(@Param(value = "identity") String identity,@Param(value = "price")int price);
}
