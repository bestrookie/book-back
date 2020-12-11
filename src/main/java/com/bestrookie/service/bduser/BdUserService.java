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
     * @param bdUserPojo 书圈信息
     * @return 自定义返回类型
     */
    MyResult joinDiscussion(BdUserPojo bdUserPojo);
    /**
     * 查询是否加入书圈
     * @param userId 用户id
     * @param discussionId 书圈id
     * @return 是否加入
     */
    boolean stateByJoin(int userId,int discussionId);
    /**
     * 查询加入书圈的人数
     * @param discussionId 书圈id
     * @return 数量
     */
    int queryNums(int discussionId);
    /**
     * 退出书圈
     * @param userId 用户id
     * @param discussionId 书圈id
     * @return 自定义返回类型
     */
    MyResult exitDiscussion(int userId,int discussionId);
    /**
     * 查询某个书圈的所有人
     * @param discussionId 书圈id
     * @return 自定返回类型
     */
    MyResult queryPopulation(int discussionId);
    /**
     * 查询某个书圈的部分人
     * @param discussionId 书圈id
     * @param limit 限制人数
     * @return 自定义返回类型
     */
    MyResult queryPopulationLimit(int discussionId,int limit);

}
