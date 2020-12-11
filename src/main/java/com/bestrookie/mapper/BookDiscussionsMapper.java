package com.bestrookie.mapper;

import com.bestrookie.pojo.BookDiscussionsPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
     * @param bookDiscussionsPojo 书圈信息实体
     * @return 是否添加成功
     */
    boolean addBookDiscussion(BookDiscussionsPojo bookDiscussionsPojo);
    /**
     * 分页查询书圈信息
     * @return 书圈信息列表
     */
    List<BookDiscussionsPojo> selectPage();
    /**
     * 根据id查找书圈
     * @param discussionId 书圈id
     * @return 书圈实体
     */
    BookDiscussionsPojo queryBookDiscussionById(int discussionId);
    /**
     * 删除书圈
     * @param discussionId 书圈id
     * @return 是否删除成功
     */
    boolean deleteBookDiscussion(@Param(value = "discussionId") int discussionId);

    /**
     * 查询是否存在书圈
     * @param bookId 书籍id
     * @return 书圈的数量
     */
    int isBookDiscussionExist(@Param(value = "bookId") int bookId);

    /**
     * 查询书圈id
     * @param bookId 书籍id
     * @return 书圈id
     */
    int queryDiscussionId(@Param(value = "bookId") int bookId);

    /**
     * 查询书圈的数量
     * @return 数量
     */
    int discussionNum();

}
