package com.bestrookie.service.booklimit;

import com.bestrookie.mapper.BookLimitMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookLimitPojo;
import com.bestrookie.pojo.BookPojo;
import com.bestrookie.pojo.UserPojo;
import com.bestrookie.service.books.BookService;
import com.bestrookie.service.user.UserService;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 19:43 2020/11/15
 */
@Service
public class BookLimitServiceImpl implements BookLimitService{
    @Autowired
    private BookLimitMapper limitMapper;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    /**
     * 获取部分权限
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    @Override
    public MyResult getBookLimit(int userId, int bookId) {
        if (isAllLimit(userId, bookId)) {
            return MyResult.failed("已经全本解锁",false,528);
        }
        BookPojo book = bookService.queryBookById(bookId);
        BookLimitPojo bookLimit = limitMapper.queryLimitById(userId, bookId);
        int total = (int)Math.ceil((double)(book.getBookPage()) / (double)100);
        int price = 0;
        try {
            price = listPrice(bookLimit.getLimitPage(),total,book.getBookPage());
        } catch (Exception e) {
            price = listPrice(0,total,book.getBookPage());
        }
        if (payCost(userId,price)){
            if (isExistBookLimit(userId, bookId)){
                if ((bookLimit.getLimitPage() + 1) < total){
                    bookLimit.setLimitPage(bookLimit.getLimitPage() + 1);
                    return updatePage(bookLimit);
                }else {
                    limitMapper.deleteLimit(userId, bookId);
                    return addLimit(userId, bookId,0);
                }
            }else {
                return  addLimit(userId, bookId,1);
            }
        }else {
            return MyResult.failed("检查你的源币是否足够",false,528);
        }


    }

    /**
     * 查询是否存在权限
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 是否存在
     */
    @Override
    public boolean isExistBookLimit(int userId, int bookId) {
        return limitMapper.isExistBookLimit(userId, bookId) != 0;
    }

    /**
     * 全部解锁
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    @Override
    public MyResult getBookAllLimit(int userId, int bookId) {
        BookPojo bookPojo = bookService.queryBookById(bookId);
        if (!isAllLimit(userId, bookId)){
            if (payCost(userId,bookPojo.getBookPrice())){
                if (isExistBookLimit(userId, bookId)){
                    if (limitMapper.updateBookLimitAll(userId, bookId)){
                        return MyResult.success(true);
                    }else {
                        return MyResult.failed("解锁失败",false,528);
                    }
                }else {
                    return addLimit(userId,bookId,0);
                }
            }else {
                return MyResult.failed("检查你的源币是否足够",false,528);
            }
        }else {
            return MyResult.failed("已经解锁",false,528);
        }

    }

    /**
     *  查询书籍权限
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    @Override
    public MyResult queryBookLimit(int userId, int bookId) {
        BookLimitPojo limitPojo = limitMapper.queryLimitById(userId, bookId);
        if (limitPojo != null){
            return MyResult.success(limitPojo);
        }else {
            BookLimitPojo bookLimitPojo = new BookLimitPojo();
            bookLimitPojo.setLimitPage(0);
            bookLimitPojo.setLimitAll(false);
            return MyResult.success(bookLimitPojo);
        }
    }

    /**
     * 查询是否全本解锁
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 是否解锁
     */
    @Override
    public boolean isAllLimit(int userId, int bookId) {
        return limitMapper.isAllLimit(userId, bookId) != 0;
    }

    /**
     * 第一次解锁(全本或者部分)
     * @param userId 用户id
     * @param bookId 书籍id
     * @param type 类型(全本或者部分)
     * @return 自定义返回类型
     */
    @Override
    public MyResult addLimit(int userId, int bookId, int type){
        BookLimitPojo limitPojo = new BookLimitPojo();
        limitPojo.setLimitPage(1);
        limitPojo.setUserId(userId);
        limitPojo.setLimitDate(System.currentTimeMillis());
        if (type == 0){
            limitPojo.setLimitAll(true);
            limitPojo.setLimitPage(0);
        }
        limitPojo.setBookId(bookId);
        if (limitMapper.addBookLimitInfo(limitPojo)){
            return MyResult.success(true);
        }else {
            return  MyResult.failed("解锁失败",false,528);
        }
    }

    /**
     * 解锁记录
     * @param param 分页参数
     * @param userId 用户id
     * @return 分页结果
     */
    @Override
    public PageResult queryBookLimitHistory(PageRequestParam param, int userId) {
        return PageUtils.getPageResult(param,getPageInfo(param,userId));
    }


    /**
     * 更新解锁部分
     * @param bookLimitPojo 解锁信息
     * @return 自定义返回类型
     */
    MyResult updatePage(BookLimitPojo bookLimitPojo){
        if (limitMapper.updateBookLimitInfo(bookLimitPojo)){
            return MyResult.success(true);
        }else {
            return MyResult.failed("解锁失败",false,528);
        }
    }

    /**
     * 扣费
     * @param userId 用户id
     * @param price 价格
     * @return 是否缴费成功
     */
    boolean payCost(int userId,int price){
        UserPojo user = userService.queryUserById(userId);
        if (user.getUserCoin() < price){
            return false;
        }else {
            return userService.updateUserCoin((user.getUserCoin() - price),user.getUserPhone());
        }
    }
    int listPrice(int part,int allPart,int pageNum){
        if (part == 0){
            return 90 * 15;
        }
        if (part +1 == allPart){
            return (pageNum - (part * 100)) * 15;
        }else {
            return 100 * 15;
        }
    }
    private PageInfo<BookLimitPojo> getPageInfo(PageRequestParam param,int userId){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<BookLimitPojo> bookLimits = limitMapper.bookLimitHistory(userId);
        return new PageInfo<>(bookLimits);
    }

}
