package com.bestrookie.service.bduser;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.BdUserPojo;

/**
 * @author : bestrookie
 * @date : 20:41 2020/10/14
 */
public interface BdUserService {
    /**
     * 加入书圈
     * @param bdUserPojo
     * @return
     */
    MyResult joinDiscussion(BdUserPojo bdUserPojo);
    /**
     * 查询是否加入书圈
     * @param userId
     * @param discussionId
     * @return
     */
    boolean stateByJoin(int userId,int discussionId);
    /**
     * 查询加入书圈的人数
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
    MyResult exitDiscussion(int userId,int discussionId);
    /**
     * 查询某个书圈的所有人
     * @param discussionId
     * @return
     */
    MyResult queryPopulation(int discussionId);
    /**
     * 查询某个书圈的部分人
     * @param discussionId
     * @param limit
     * @return
     */
    MyResult queryPopulationLimit(int discussionId,int limit);

}
