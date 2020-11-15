package com.bestrookie.service.books;

import com.bestrookie.mapper.BookMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookPojo;
import com.bestrookie.pojo.UserPojo;
import com.bestrookie.service.bookreview.BookReviewService;
import com.bestrookie.service.collection.CollectionService;
import com.bestrookie.service.user.UserService;
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
    @Autowired
    private UserService userService;
    private static final int UN = 0;
    private static final int FUZZY = 1;
    private static final int FUZZY_TYPE = 2;
    private static final int SEARCH_ID = 3;
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
     * 模糊查询
     * @param param 分页参数
     * @param key 关键词
     * @return 将查询结果分页
     */
    @Override
    public PageResult queryFuzzy(PageRequestParam param, String key,int typeId) {
        if (typeId == 0){
            return PageUtils.getPageResult(param,getFuzzyInfo(param,key,0,FUZZY));
        }else {
            return PageUtils.getPageResult(param,getFuzzyInfo(param,key,typeId,FUZZY_TYPE));
        }
    }

    /**
     * 搜索中的搜索id
     * @param param 分页信息
     * @param bookId 书籍id
     * @return 分页结果
     */
    @Override
    public PageResult queryById(PageRequestParam param, int bookId) {
        return PageUtils.getPageResult(param,getFuzzyInfo(param,null,bookId,SEARCH_ID));
    }

    /**
     *管理员修改状态码
     * @param bookId 书籍id
     * @param status 状态
     * @return 自定义返回类型
     */
    @Override
    public MyResult updateStatus(int bookId, boolean status) {
        if (bookMapper.updateStateById(bookId,status)){
            if (!status){
                BookPojo bookPojo = bookMapper.queryBookById(bookId);
                UserPojo userPojo = userService.queryUserById(bookPojo.getUserId());
                int coin = (int)(bookPojo.getBookPrice() / 1.1);
                userService.updateUserCoin(userPojo.getUserCoin() - coin,userPojo.getUserPhone());
            }
            return MyResult.success(true);
        }else {
            return MyResult.failed("修改状态失败",false,525);
        }
    }

    /**
     * 修改书籍信息
     * @param bookPojo 书籍信息
     * @return 自定义返回类型
     */
    @Override
    public MyResult updateBookInfo(BookPojo bookPojo) {
        if (bookMapper.updateBookInfo(bookPojo)){
            return MyResult.success(true);
        }else {
            return MyResult.failed("修改书籍信息失败",false,526);
        }
    }

    /**
     * 根据类型查询点击量排名
     * @param typeId 类型id
     * @return 自定义返回类型
     */
    @Override
    public MyResult booksOutByType(int typeId) {
        List<BookPojo> books = bookMapper.booksAreRankByType(typeId);
        if (books != null){
            return MyResult.success(books);
        }else {
            return MyResult.failed("查看分类榜失败",false,527);
        }
    }

    /**
     * 查看未过审书籍
     * @param param 分页参数
     * @return 分页结果
     */
    @Override
    public PageResult queryUnBook(PageRequestParam param) {
        return PageUtils.getPageResult(param,getFuzzyInfo(param,null,0,UN));
    }

    /**
     * 查看此书是否过审
     * @param bookId 书籍id
     * @return 是否过审
     */
    @Override
    public boolean isBookTrue(int bookId) {
        BookPojo bookPojo = bookMapper.queryBookById(bookId);
        return bookPojo.isBookState();
    }

    /**
     * 查看书籍排行耪
     * @return 自定义
     */
    @Override
    public MyResult queryTop() {
        List<BookPojo> books = bookMapper.bookTop();
        if (books != null){
            return MyResult.success(books);
        }else {
            return  MyResult.failed("查询排行榜失败",null,527);
        }
    }

    @Override
    public boolean addCollection(int bookId) {
        return bookMapper.addCollection(bookId);
    }

    @Override
    public boolean reduceCollection(int bookId) {
        return bookMapper.reduceCollection(bookId);
    }

    /**
     * 调用分页插件
     * @param param 分页参数
     * @param userId 用户id
     * @return 分页结果
     */
    private PageInfo<BookPojo> getBookInfo(PageRequestParam param,int userId){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<BookPojo> books = bookMapper.queryMyUpload(userId);
        return new PageInfo<>(books);
    }

    /**
     * 调用分页插件处理模糊查询等
     * @param param 分页参数
     * @param key 关键词
     * @param typeId 类型id
     * @param type 查询类型
     * @return 分页结果
     */
    private PageInfo<BookPojo> getFuzzyInfo(PageRequestParam param,String key,int typeId,int type){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<BookPojo> books = new ArrayList<>();
        switch (type){
            case 0:
                books = bookMapper.queryUnBook();
                break;
            case 1:
                books = bookMapper.queryFuzzy(key);
                break;
            case 2:
                books = bookMapper.queryFuzzyByType(key,typeId);
                break;
            case 3:
                books.add(bookMapper.queryBookById(typeId));
                break;
            default:
                break;
        }
        return new PageInfo<>(books);
    }
}
