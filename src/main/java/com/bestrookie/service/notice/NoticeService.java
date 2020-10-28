package com.bestrookie.service.notice;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.NoticePojo;

/**
 * @author : bestrookie
 * @date : 20:04 2020/10/27
 */
public interface NoticeService {
    /**
     * 用户查询公告
     * @param param 分页参数
     * @param userId 用户id
     * @return
     */
    PageResult queryNotice(PageRequestParam param,int userId);
    /**
     * 管理员查询所有公告
     * @param param
     * @return
     */
    PageResult queryAllNotice(PageRequestParam param);
    /**
     * 管理员删除公告
     * @param noticeId
     * @return
     */
    MyResult deleteNotice(int noticeId);
    /**
     * 发布公告
     * @param noticePojo
     * @return
     */
    MyResult releaseNotice(NoticePojo noticePojo);
    /**
     * 根据id查询公告
     * @param noticeId
     * @return
     */
    NoticePojo queryNoticeById(int noticeId);
}
