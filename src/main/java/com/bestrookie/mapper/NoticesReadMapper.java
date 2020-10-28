package com.bestrookie.mapper;

import com.bestrookie.pojo.NoticesReadPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author : bestrookie
 * @date : 16:25 2020/10/27
 */
@Mapper
@Repository
public interface NoticesReadMapper {
    /**
     * 添加已读信息
     * @param noticesReadPojo 已读消息实体
     * @return 是否添加成功
     */
    boolean addNoticeRead(NoticesReadPojo noticesReadPojo);
    /**
     * 查询是否已读这条消息
     * @param userId 用户id
     * @param noticeId 公告id
     * @return 存在返回对象
     */
    int queryNoticesRead(@Param(value = "userId") int userId, @Param(value = "noticeId") int noticeId);
    /**
     * 统计已读人数
     * @param noticeId 公告id
     * @return 人数
     */
    int noticesReadNum(int noticeId);
}
