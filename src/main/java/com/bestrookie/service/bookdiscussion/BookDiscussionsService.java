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
     * @param bookDiscussionsPojo 书圈信息实体
     * @return 自定义返回类型
     */
    MyResult addBookDiscussion(BookDiscussionsPojo bookDiscussionsPojo);
    /**
     * 分页查询书圈信息
     * @param param 分页参数
     * @return 自定义返回类型
     */
    PageResult queryDiscussion(PageRequestParam param);
    /**
     * 根据书圈id查询信息
     * @param userId 用户id
     * @param discussionId 书圈id
     * @return 自定义返回类型
     */
    MyResult queryDiscussionById(int userId,int discussionId);

    /**
     * 删除书圈
     * @param discussionId 书圈id
     * @return 自定义返回类型
     */
    MyResult deleteDiscussion(int discussionId);

    /**
     * 查询这本书是否存在书圈
     * @param bookId 书籍id
     * @return 是否存在
     */
    boolean isDiscussionExist(int bookId);

}
