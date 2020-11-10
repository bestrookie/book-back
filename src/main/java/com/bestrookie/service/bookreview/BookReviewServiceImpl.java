package com.bestrookie.service.bookreview;

import com.bestrookie.mapper.BookReviewMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookReviewPojo;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author : bestrookie
 * @date : 9:59 2020/11/10
 */
@Service
public class BookReviewServiceImpl implements BookReviewService {
    @Autowired
    private BookReviewMapper bookReviewMapper;

    /**
     * 发表评论
     * @param bookReviewPojo 评论信息
     * @return 自定义返回类型
     */
    @Override
    public MyResult postComment(BookReviewPojo bookReviewPojo) {
        if (!isReview(bookReviewPojo.getUserId(),bookReviewPojo.getBookId())){
            boolean flg = bookReviewMapper.addBookReview(bookReviewPojo);
            if (flg){
                return MyResult.success(true,"评论成功");
            }else {
                return MyResult.failed("评论失败",false,520);
            }
        }else {
            return MyResult.failed("只能评论一次哦",false,420);
        }
    }

    /**
     * 查看所有评论
     * @param param 分页参数
     * @param bookId 书籍id
     * @return 分页结果
     */
    @Override
    public PageResult queryAllBookReview(PageRequestParam param, int bookId) {
        return PageUtils.getPageResult(param,getBookReviewInfo(param,bookId));
    }

    /**
     * 是否评论过此书籍
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 是否评论
     */
    @Override
    public boolean isReview(int userId, int bookId) {
        int review = bookReviewMapper.isReview(userId, bookId);
        return review != 0;
    }

    /**
     * 计算书籍总评分
     * @param bookId 书籍id
     * @return 总评分
     */
    @Override
    public double countBookValue(int bookId) {
        int sun = 0;
        double result = 0;
        List<BookReviewPojo> bookReviews = bookReviewMapper.queryAllReviews(bookId);
        if (bookReviews.size() == 0){
            return result;
        }
        for (BookReviewPojo bookReview : bookReviews) {
            sun = sun + bookReview.getValue();
        }
        result =  (double) sun / (double)bookReviews.size();
        DecimalFormat f = new DecimalFormat("#.0");
        return Double.parseDouble(f.format(result));
    }

    /**
     * 调用分页工具进行分页
     * @param param 分页参数
     * @param bookId 查询参数
     * @return 分页结果
     */
    private PageInfo<BookReviewPojo> getBookReviewInfo(PageRequestParam param,int bookId){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<BookReviewPojo> bookReviews = bookReviewMapper.queryAllReviews(bookId);
        return new PageInfo<>(bookReviews);
    }
}
