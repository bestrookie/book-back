package com.bestrookie.mapper;

import com.bestrookie.pojo.BookDiscussionsPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 10:28 2020/10/13
 */
@Repository
@Mapper
public interface BookDiscussionsMapper {
    /**
     * 添加书圈
     * @param bookDiscussionsPojo
     * @return
     */
    boolean addBookDiscussion(BookDiscussionsPojo bookDiscussionsPojo);
    /**
     * 分页查询书圈信息
     * @return
     */
    List<BookDiscussionsPojo> selectPage();
    /**
     * 根据id查找书圈
     * @param discussionId
     * @return
     */
    BookDiscussionsPojo queryBookDiscussionById(int discussionId);

}
