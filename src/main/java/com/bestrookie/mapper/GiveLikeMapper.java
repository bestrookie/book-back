package com.bestrookie.mapper;

/**
 * @author : bestrookie
 * @date : 20:42 2020/10/16
 */
import com.bestrookie.pojo.GiveLikePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GiveLikeMapper {
    /**
     *添加点赞信息
     * @param giveLikePojo
     * @return
     */
    boolean addGiveLike(GiveLikePojo giveLikePojo);
    /**
     * 获取点赞数
     * @param dynamicId
     * @return
     */
    int giveLikeNum(int dynamicId);
    /**
     * 是否点赞
     * @param dynamicId
     * @param userId
     * @return
     */
    GiveLikePojo isLiked(@Param("dynamicId") int dynamicId, @Param(value = "userId") int userId);
    /**
     * 根据动态id和userId删除点赞信息
     * @param dynamicId
     * @param userId
     * @return
     */
    boolean deleteDynamicById(@Param("dynamicId") int dynamicId, @Param("userId") int userId);
}
