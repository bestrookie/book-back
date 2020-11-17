package com.bestrookie.service.bookhistory;

import com.bestrookie.mapper.BookHistoryMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookHistoryPojo;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 20:32 2020/11/17
 */
@Service
public class BookHistoryServiceImpl implements BookHistoryService{
    @Autowired
    private BookHistoryMapper historyMapper;
    @Override
    public boolean isHistory(int userId, int bookId) {
        return historyMapper.isHistory(userId, bookId) != 0;
    }

    /**
     * 添加记录
     * @param historyPojo 记录信息
     * @return 自定义返回类型
     */
    @Override
    public MyResult addHistory(BookHistoryPojo historyPojo) {
        if (isHistory(historyPojo.getUserId(),historyPojo.getBookId())){
            return MyResult.failed("已经存在记录",false,529);
        }else {
            if (historyMapper.addBookHistory(historyPojo)){
                return MyResult.success(true);
            }else {
                return MyResult.failed("创建历史失败",false,529);
            }
        }
    }

    /**
     * 查看历史
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    @Override
    public MyResult queryHistory(int userId, int bookId) {
        BookHistoryPojo bookHistoryPojo = historyMapper.queryBookHistory(userId, bookId);
        if (historyMapper!=null){
            return MyResult.success(bookHistoryPojo);
        }else {
            return MyResult.failed("查看历史失败",false,529);
        }
    }

    @Override
    public PageResult queryAllHistory(PageRequestParam param, int userId) {
        return PageUtils.getPageResult(param,getPageInfo(param,userId));
    }

    /**
     *  修改历史
     * @param historyPojo 历史信息
     * @return 自定义返回类型
     */
    @Override
    public MyResult updateHistory(BookHistoryPojo historyPojo) {
        if (historyMapper.updateBookHistory(historyPojo)){
            return MyResult.success(true);
        }else {
            return MyResult.failed("修改历史失败",false,529);
        }
    }

    private PageInfo<BookHistoryPojo> getPageInfo(PageRequestParam param,int userId){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<BookHistoryPojo> bookHistory = historyMapper.queryBookHistoryById(userId);
        return new PageInfo<>(bookHistory);
    }
}
