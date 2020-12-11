package com.bestrookie.service.givelike;

import com.bestrookie.mapper.GiveLikeMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.GiveLikePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author : bestrookie
 * @date : 8:53 2020/10/17
 */
@Service
public class GiveLikeServiceImpl implements GiveLikeService {
    @Autowired
    private GiveLikeMapper giveLikeMapper;
    /**
     * 添加点赞信息
     * @param giveLikePojo 点赞信息
     * @return 自定义返回类型
     */
    @Override
    public MyResult giveLike(GiveLikePojo giveLikePojo) {
        if (!isLiked(giveLikePojo.getDynamicId(), giveLikePojo.getUserId())){
            if (giveLikePojo.getUserId() > 0 || giveLikePojo.getDynamicId() > 0){
                if (giveLikeMapper.addGiveLike(giveLikePojo)){
                    HashMap<String, Object>result = new HashMap<>();
                    result.put("likeNum",giveLikeNum(giveLikePojo.getDynamicId()));
                    result.put("isLike",isLiked(giveLikePojo.getDynamicId(),giveLikePojo.getUserId()));
                    return MyResult.success(result,"点赞成功");
                }else {
                    return  MyResult.failed("点赞失败",false,510);
                }
            }else {
                return MyResult.failed("传入id参数错误",null,410);
            }

        }else {
            return MyResult.failed("已经点赞，请勿重复",null,410);
        }
    }

    /**
     * 获取点赞数
     * @param dynamicId 动态id
     * @return 自定义返回类型
     */
    @Override
    public int giveLikeNum(int dynamicId) {
        return giveLikeMapper.giveLikeNum(dynamicId);
    }
    /**
     * 是否点赞
     * @param dynamicId 动态id
     * @param userId 用户id
     * @return 自定义返回类型
     */
    @Override
    public boolean isLiked(int dynamicId, int userId) {
        return giveLikeMapper.isLiked(dynamicId, userId) != null;
    }
    /**
     * 取消点赞
     * @param dynamicId 动态id
     * @param userId 用户id
     * @return 自定义返回类型
     */
    @Override
    public MyResult cancelLiked(int dynamicId, int userId) {
        if (isLiked(dynamicId,userId)){
            if (giveLikeMapper.deleteDynamicById(dynamicId,userId)){
                HashMap<String, Object> result = new HashMap<>();
                result.put("likeNum",giveLikeNum(dynamicId));
                result.put("isLike",isLiked(dynamicId,userId));
                return MyResult.success(result,"取消点赞");
            }else {
                return  MyResult.failed("取消点赞失败",null,510);
            }
        }else {
            return MyResult.failed("尚未点赞",null,410);
        }
    }
}
