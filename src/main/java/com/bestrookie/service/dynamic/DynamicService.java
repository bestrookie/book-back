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
    PageResult queryDynamic(PageRequestParam param,int discussionId);
    /**
     * 发布动态
     * @param dynamicPojo
     * @return
     */
    MyResult releaseDynamic(DynamicPojo dynamicPojo);
}
