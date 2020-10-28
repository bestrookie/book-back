package com.bestrookie.service.noticesread;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.NoticesReadPojo;
import org.apache.ibatis.annotations.Param;

/**
 * @author : bestrookie
 * @date : 19:38 2020/10/27
 */
public interface NoticesReadService {
    /**
     * 添加已读公告信息
     * @param readPojo 已读公告实体
     * @return 自定义返回类型
     */
    MyResult addNoticeRead(NoticesReadPojo readPojo);
    /**
     * 查询是否已读这条消息
     * @param userId 用户id
     * @param noticeId 公告id
     * @return 存在返回对象
     */
    boolean isReadNotice(int userId, int noticeId);
}
