package com.bestrookie.service.type;

import com.bestrookie.mapper.BookTypeMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.BookTypePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 19:42 2020/11/7
 */
@Service
public class BookTypeServiceImpl implements BookTypeService{
    @Autowired
    private BookTypeMapper bookTypeMapper;
    /**
     * 查询书籍类型
     * @return
     */
    @Override
    public MyResult queryBookType() {
        List<BookTypePojo> bookTypes = bookTypeMapper.queryBookType();
        if (bookTypes != null){
            return MyResult.success(bookTypes,"查询成功");
        }else {
            return  MyResult.failed("查询失败",null,518);
        }
    }
}
