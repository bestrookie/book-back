package com.bestrookie.service.bduser;

import com.bestrookie.mapper.BdUserMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.BdUserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author : bestrookie
 * @date : 20:45 2020/10/14
 */
@Service
public class BdUserServiceImpl implements BdUserService {
    @Autowired
    BdUserMapper bdUserMapper;
    /**
     * 加入书圈
     * @param bdUserPojo 书圈关系信息
     * @return 自定义返回类型
     */
    @Override
    public MyResult joinDiscussion(BdUserPojo bdUserPojo) {
        if (stateByJoin(bdUserPojo.getUserId(),bdUserPojo.getBdId())){
            return MyResult.failed("已经加入书圈",null,408);
        }else {
            if (bdUserMapper.joinDiscussion(bdUserPojo)){
                HashMap<String, Object> result = new HashMap<>();
                result.put("state",stateByJoin(bdUserPojo.getUserId(),bdUserPojo.getBdId()));
                result.put("num",queryNums(bdUserPojo.getBdId()));
                return MyResult.success(result);
            }else {
                return MyResult.failed("加入失败",null,508);
            }
        }
    }
    /**
     * 查询是否已经加入书圈
     * @param userId 用户id
     * @param discussionId 书圈id
     * @return 是否加入
     */
    @Override
    public boolean stateByJoin(int userId, int discussionId) {
        return bdUserMapper.queryById(userId, discussionId) != null;
    }
    /**
     * 查询加入书圈的人数
     * @param discussionId 书圈id
     * @return 人数
     */
    @Override
    public int queryNums(int discussionId) {
        return bdUserMapper.queryNums(discussionId);
    }
    /**
     * 退出书圈
     * @param userId 用户id
     * @param discussionId 书圈id
     * @return 自定义返回类型
     */
    @Override
    public MyResult exitDiscussion(int userId, int discussionId) {
        if (stateByJoin(userId,discussionId)){
            if (bdUserMapper.exitDiscussion(userId,discussionId)){
                HashMap<String, Object> result = new HashMap<>();
                result.put("state",stateByJoin(userId,discussionId));
                result.put("num",queryNums(discussionId));
                return MyResult.success(result,"成功退出书圈");
            }else {
                return MyResult.failed("退出书圈出错",508);
            }
        }else {
            return MyResult.failed("尚未加入书圈",null,408);
        }
    }
    /**
     * 查询某个书圈的所有人
     * @param discussionId 书圈id
     * @return 自定义返回类型
     */
    @Override
    public MyResult queryPopulation(int discussionId) {
        if (discussionId > 0){
            return MyResult.success(bdUserMapper.queryPopulation(discussionId),"书圈所有人");
        }else {
            return MyResult.failed("传入参数错误","409");
        }
    }
    /**
     * 查询某个书圈的部分人
     * @param discussionId 书圈id
     * @param limit 人数限制
     * @return 自定义返回类型
     */
    @Override
    public MyResult queryPopulationLimit(int discussionId, int limit) {
        if(discussionId>0 && limit > 0){
            return MyResult.success(bdUserMapper.queryPopulationLimit(discussionId,limit),"书圈部分人");
        }else {
            return MyResult.failed("参数错误",null,409);
        }
    }
}
