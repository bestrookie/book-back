package com.bestrookie.mapper;

import com.bestrookie.pojo.BdUserPojo;
import com.bestrookie.pojo.DiscussionUserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 20:25 2020/10/14
 */
@Repository
@Mapper
public interface BdUserMapper {
    /**
     * 加入书圈
     * @param bdUserPojo 书圈关系实体
     * @return 是否加入成功
     */
    boolean joinDiscussion(BdUserPojo bdUserPojo);
    /**
     * 根据书圈id和用户id查询是否加入书圈
     * @param userId 用户id
     * @param discussionId 书圈id
     * @return 书圈关系实体
     */
    BdUserPojo queryById(int userId,int discussionId);
    /**
     * 查询书圈人数
     * @param discussionId 书圈id
     * @return 人数
     */
    int queryNums(int discussionId);
    /**
     * 退出书圈
     * @param userId 用户id
     * @param discussionId 书圈id
     * @return 是否成功
     */
    boolean exitDiscussion(int userId,int discussionId);
    /**
     * 查询某一个书圈的所有人
     * @param discussionId 书圈
     * @return 书圈列表
     */
    List<DiscussionUserPojo> queryPopulation(int discussionId);
    /**
     * 查询某一个书圈的部分人
     * @param discussionId 书圈id
     * @param limit 限制条数
     * @return 书圈关系表
     */
    List<DiscussionUserPojo> queryPopulationLimit(@Param(value = "discussionId") int discussionId, @Param(value = "limit") int limit);
}
