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
     * @param param
     * @param discussionId
     * @return
     */
    PageResult queryDynamic(PageRequestParam param,int discussionId,int userId);
    /**
     * 发布动态
     * @param dynamicPojo
     * @return
     */
    MyResult releaseDynamic(DynamicPojo dynamicPojo);
    /**
     * 查看动态详情
     * @param dynamicId
     * @param userId
     * @return
     */
    MyResult queryDynamicById(int dynamicId,int userId);

    /**
     * 删除动态
     * @param dynamicId
     * @param userId
     * @return
     */
    MyResult deleteDynamicById(int dynamicId,int userId);
}
