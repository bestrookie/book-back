package com.bestrookie.service.books;

import com.bestrookie.mapper.BookMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookPojo;
import com.bestrookie.service.bookreview.BookReviewService;
import com.bestrookie.service.collection.CollectionService;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : bestrookie
 * @date : 19:18 2020/11/7
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookReviewService bookReviewService;
    @Autowired
    private CollectionService collectionService;

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
    /**
     * 根据id查询书籍信息
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    @Override
    public MyResult queryBookById(int bookId,int userId) {
        BookPojo bookPojo = bookMapper.queryBookById(bookId);
        if (bookPojo != null){
            bookPojo.setBookClick(bookPojo.getBookClick() + 1);
            bookMapper.updateBookSearch(bookPojo.getBookId(),bookPojo.getBookClick());
            bookPojo.setMyReview(bookReviewService.isReview(userId,bookId));
            bookPojo.setValue(bookReviewService.countBookValue(bookId));
            bookPojo.setMyCollection(collectionService.isCollection(userId,bookId));
            return MyResult.success(bookPojo,"查询成功");
        }else {
            return MyResult.failed("查询失败",null,518);
        }
    }

    /**
     * 上传历史
     * @param userId 用户id
     * @param param 分页参数
     * @return 分页结果
     */
    @Override
    public PageResult queryMyUpload(PageRequestParam param, int userId) {
        return PageUtils.getPageResult(param,getBookInfo(param,userId));
    }

    /**
     * 查看用户收藏
     * @param userId 用户id
     * @return 分页结果
     */
    @Override
    public MyResult queryMyCollect(int userId) {
        List<BookPojo> books = bookMapper.queryMyCollection(userId);
        if (books.size() == 0){
            return MyResult.success(null,"书架是空的哦");
        }else {
            return MyResult.success(books);
        }
    }

    /**
     * 调用分页插件
     * @param param 分页参数
     * @param userId 用户id
     * @return 分页结果
     */
    private PageInfo<BookPojo> getBookInfo(PageRequestParam param,int userId){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<BookPojo> books = new ArrayList<>();
        books = bookMapper.queryMyUpload(userId);
        return new PageInfo<>(books);

    }
}
