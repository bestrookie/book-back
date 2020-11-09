package com.bestrookie.service.books;

import com.bestrookie.mapper.BookMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.BookPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : bestrookie
 * @date : 19:18 2020/11/7
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    /**
     * 上传书籍
     * @param bookPojo 书籍信息实体
     * @return 自定义返回类型
     */
    @Override
    public MyResult uploadBook(BookPojo bookPojo) {
        if (bookMapper.addBookMapper(bookPojo)){
            return MyResult.success(true,"上传成功");
        }else {
            return  MyResult.failed("上传失败",false,517);
        }
    }

    /**
     * 查pdf是否存在
     * @param identity 身份码
     * @return 自定义返回类型
     */
    @Override
    public MyResult queryPdfExists(String identity) {
        if (bookMapper.queryExistsIdentity(identity) == 0){
            return MyResult.success(false,"pdf不存在");
        }else {
            return MyResult.success(true,"pdf存在");
        }
    }

    /**
     * 根据身份码修改状态
     * @param identity 身份码
     * @param state 状态
     */
    @Override
    public void updateBookState(String identity, boolean state) {
        bookMapper.updateBookState(identity, state);
    }

    /**
     * 根据身份码修改价格
     * @param identity 身份码
     * @param price 价格
     */
    @Override
    public void updateBookPriceByIdentity(String identity, int price) {
        bookMapper.updateBookPriceByIdentity(identity, price);
    }
}
