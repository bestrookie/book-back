package com.bestrookie.mapper;

import com.bestrookie.pojo.NoticePojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 10:50 2020/10/27
 */
@Mapper
@Repository
public interface NoticeMapper {
    /**
     * 添加公告
     * @param noticePojo 公告实体
     * @return 是否添加成功
     */
    boolean addNotice(NoticePojo noticePojo);
    /**
     * 查询所有公告
     * @return 公告列表
     */
    List<NoticePojo> queryNotice();

    /**
     * 删除公告
     * @param noticeId 公告id
     * @return 是否删除成功
     */
    boolean deleteNotice(int noticeId);

    /**
     * 根据id查询公告
     * @param noticeId 公告id
     * @return 公告信息
     */
    NoticePojo queryNoticeById(int noticeId);
}
