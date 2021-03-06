package com.bestrookie.service.collection;

import com.bestrookie.mapper.CollectionMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.CollectionPojo;
import com.bestrookie.service.books.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : bestrookie
 * @date : 20:31 2020/11/10
 */
@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private BookService bookService;
    @Override
    public MyResult addCollection(CollectionPojo collectionPojo) {
        if (bookService.isBookTrue(collectionPojo.getBookId())){
            if (!isCollection(collectionPojo.getUserId(),collectionPojo.getBookId())){
                if (collectionMapper.addCollection(collectionPojo)){
                    bookService.addCollection(collectionPojo.getBookId());
                    return MyResult.success("添加收藏成功");
                }else {
                    return MyResult.failed("添加收藏失败",false,522);
                }
            }else {
                return MyResult.failed("已经添加收藏了哦",false,522);
            }
        }else {
            return MyResult.failed("此书已下架",false,522);
        }

    }

    @Override
    public MyResult cancelCollection(int userId, int bookId) {
        if (isCollection(userId, bookId)){
            if (collectionMapper.cancelCollection(userId, bookId)){
                bookService.reduceCollection(bookId);
                return MyResult.success("取消收藏成功");
            }else {
                return MyResult.failed("取消收藏失败",false,522);
            }
        }else {
            return MyResult.failed("还没收藏哦",false,522);
        }
    }

    @Override
    public boolean isCollection(int userId, int bookId) {
        int collection = collectionMapper.isCollection(userId, bookId);
        return collection != 0;
    }
}
