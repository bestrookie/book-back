package com.bestrookie.service.dynamicReview;

import com.bestrookie.mapper.DynamicMapper;
import com.bestrookie.mapper.DynamicReviewMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.DynamicPojo;
import com.bestrookie.pojo.DynamicReviewPojo;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 15:48 2020/10/19
 */
@Service
public class DynamicReviewServiceImpl implements DynamicReviewService{
    @Autowired
    private DynamicReviewMapper dynamicReviewMapper;
    @Autowired
    private DynamicMapper dynamicMapper;

    /**
     * 查询所有评论
     * @param param 分页参数
     * @param dynamicId 动态id
     * @return 自定义返回类型
     */
    @Override
    public PageResult queryDynamicReview(PageRequestParam param, int dynamicId) {
        return PageUtils.getPageResult(param,getDynamicReviewInfo(param,dynamicId));
    }
    /**
     * 删除评论
     * @param dynamicId 动态id
     * @param reviewId 评论id
     * @param userId 用户id
     * @return 自定义返回类型
     */
    @Override
    public MyResult deleteDynamicReview(int dynamicId, int reviewId, int userId) {
        DynamicPojo dynamicPojo = dynamicMapper.queryDynamicById(dynamicId);
        if (dynamicPojo.getUser().getUserId() == userId){
            if (dynamicReviewMapper.deleteReview(reviewId)){
                return MyResult.success(true,"删除成功");
            }else {
                return MyResult.failed("删除失败",null,511);
            }
        }else {
            if (dynamicReviewMapper.deleteReviewById(reviewId, userId)){
                return MyResult.success(true,"删除成功");
            }else {
                return MyResult.failed("删除失败",null,511);
            }
        }
    }

    /**
     * 发布评论
     * @param dynamicReviewPojo 评论信息
     * @return 自定义返回类型
     */
    @Override
    public MyResult releaseDynamicReview(DynamicReviewPojo dynamicReviewPojo) {
        try{
            if (dynamicReviewPojo != null){
                if (dynamicReviewMapper.addReview(dynamicReviewPojo)){
                    return MyResult.success(true,"发布成功");
                }else {
                    return MyResult.failed("发布失败",false,511);
                }
            }else {
                return MyResult.failed("信息为空",false,411);
            }
        }catch (Exception e){
            return MyResult.failed("原评论已删除",false,511);
        }

    }

    @Override
    public int reviewCount(int dynamicId) {
        return dynamicReviewMapper.reviewCount(dynamicId);
    }

    /**
     * 调用分页插件完成分页
     * @param param 分页参数
     * @param dynamicId 动态id
     * @return 分页结果
     */
    private PageInfo<DynamicReviewPojo> getDynamicReviewInfo(PageRequestParam param,int dynamicId){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<DynamicReviewPojo> dynamicReviews = dynamicReviewMapper.queryDynamicReviews(dynamicId);
        return new PageInfo<>(dynamicReviews);
    }
}
