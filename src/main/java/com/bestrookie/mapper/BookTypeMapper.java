package com.bestrookie.mapper;

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
     * @return
     */
    List<BookTypePojo> queryBookType();
}
