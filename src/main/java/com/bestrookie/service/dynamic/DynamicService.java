package com.bestrookie.service.dynamic;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.DynamicPojo;

/**
 * @author : bestrookie
 * @date : 20:13 2020/10/15
 */
public interface DynamicService {
    /**
     * 查询动态信息
     * @param param 分页参数
     * @param discussionId 书圈id
     * @param userId 用户id
     * @return 分页查询结果
     */
    PageResult queryDynamic(PageRequestParam param,int discussionId,int userId);
    /**
     * 发布动态
     * @param dynamicPojo 动态实体信息
     * @return 自定义返回类型
     */
    MyResult releaseDynamic(DynamicPojo dynamicPojo);
    /**
     * 查看动态详情
     * @param dynamicId 动态id
     * @param userId 用户id
     * @return 自定义返回类型
     */
    MyResult queryDynamicById(int dynamicId,int userId);

    /**
     * 删除动态
     * @param dynamicId 动态id
     * @param userId 用户id
     * @return 自定义返回类型
     */
    MyResult deleteDynamicById(int dynamicId,int userId);

    /**
     * 分页查询所有动态信息
     * @param param 分页参数
     * @return 自定义返回类型
     */
    PageResult queryAllDynamic(PageRequestParam param);

    /**
     * 管理院删除动态
     * @param dynamicId 动态id
     * @return 自定义返回类型
     */
    MyResult deleteDynamic(int dynamicId);
}
