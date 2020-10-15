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
     * @param bdUserPojo
     * @return
     */
    boolean joinDiscussion(BdUserPojo bdUserPojo);
    /**
     * 根据书圈id和用户id查询是否加入书圈
     * @param userId
     * @param discussionId
     * @return
     */
    BdUserPojo queryById(int userId,int discussionId);
    /**
     * 查询书圈人数
     * @param discussionId
     * @return
     */
    int queryNums(int discussionId);
    /**
     * 退出书圈
     * @param userId
     * @param discussionId
     * @return
     */
    boolean exitDiscussion(int userId,int discussionId);
    /**
     * 查询某一个书圈的所有人
     * @param discussionId
     * @return
     */
    List<DiscussionUserPojo> queryPopulation(int discussionId);
    /**
     * 查询某一个书圈的部分人
     * @param discussionId
     * @param limit
     * @return
     */
    List<DiscussionUserPojo> queryPopulationLimit(@Param(value = "discussionId") int discussionId, @Param(value = "limit") int limit);
}
