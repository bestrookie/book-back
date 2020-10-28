package com.bestrookie.service.noticesread;

import com.bestrookie.mapper.NoticesReadMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.NoticesReadPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : bestrookie
 * @date : 19:54 2020/10/27
 */
@Service
public class NoticesReadServiceImpl implements NoticesReadService {
    @Autowired
    private NoticesReadMapper readMapper;
    /**
     * 添加已读公告信息
     * @param readPojo 已读公告实体
     * @return 自定义返回类型
     */
    @Override
    public MyResult addNoticeRead(NoticesReadPojo readPojo) {
        if (readMapper.addNoticeRead(readPojo)){
            return MyResult.success(true,"添加成功");
        }else {
            return MyResult.failed("添加失败",515);
        }
    }
    /**
     * 查询是否已读这条消息
     * @param userId 用户id
     * @param noticeId 公告id
     * @return 存在返回对象
     */
    @Override
    public boolean isReadNotice(int userId, int noticeId) {
        if (readMapper.queryNoticesRead(userId, noticeId) == 0){
            return false;
        }else {
            return true;
        }
    }
}
