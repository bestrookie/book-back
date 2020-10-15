package com.bestrookie.service.bookdiscussion;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookDiscussionsPojo;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 16:21 2020/10/13
 */
public interface BookDiscussionsService {
    /**
     * 添加书圈
     * @param bookDiscussionsPojo
     * @return
     */
    MyResult addBookDiscussion(BookDiscussionsPojo bookDiscussionsPojo);
    /**
     * 分页查询书圈信息
     * @param param
     * @return
     */
    PageResult queryDiscussion(PageRequestParam param);
    /**
     * 根据书圈id查询信息
     * @param userId
     * @param discussionId
     * @return
     */
    MyResult queryDiscussionById(int userId,int discussionId);
}
