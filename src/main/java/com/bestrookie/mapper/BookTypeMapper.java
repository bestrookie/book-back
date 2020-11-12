package com.bestrookie.mapper;

import com.bestrookie.pojo.BookPojo;
import com.bestrookie.pojo.BookTypePojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 19:36 2020/11/7
 */
@Repository
@Mapper
public interface BookTypeMapper {
    /**
     * 查询所有的书籍类型
     * @return 类型列表
     */
    List<BookTypePojo> queryBookType();

    /**
     * 添加书籍类型
     * @param bookTypePojo 类型信息
     * @return 是否添加成功
     */
    boolean addBookType(BookTypePojo bookTypePojo);

    /**
     * 修改书籍类型
     * @param bookTypePojo 书籍类型信息
     * @return 是否修改成功
     */
    boolean updateBookType(BookTypePojo bookTypePojo);
}
