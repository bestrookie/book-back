package com.bestrookie.mapper;

import com.bestrookie.pojo.GiveLikePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GiveLikeMapper {
    /**
     *添加点赞信息
     * @param giveLikePojo 点赞信息
     * @return 自定义返回类型
     */
    boolean addGiveLike(GiveLikePojo giveLikePojo);
    /**
     * 获取点赞数
     * @param dynamicId 动态id
     * @return 自定义返回类型
     */
    int giveLikeNum(int dynamicId);
    /**
     * 是否点赞
     * @param dynamicId 动态id
     * @param userId 用户id
     * @return 自定义返回类型
     */
    GiveLikePojo isLiked(@Param("dynamicId") int dynamicId, @Param(value = "userId") int userId);
    /**
     * 根据动态id和userId删除点赞信息
     * @param dynamicId 动态id
     * @param userId 用户id
     * @return 自定义返回类型
     */
    boolean deleteDynamicById(@Param("dynamicId") int dynamicId, @Param("userId") int userId);
}
