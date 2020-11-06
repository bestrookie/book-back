package com.bestrookie.service.bookdiscussion;

import com.bestrookie.mapper.BookDiscussionsMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookDiscussionsPojo;
import com.bestrookie.service.bduser.BdUserService;
import com.bestrookie.service.userbanned.UserBannedService;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : bestrookie
 * @date : 16:21 2020/10/13
 */
@Service
public class BookDiscussionsServiceImpl implements BookDiscussionsService {
    @Autowired
    private BookDiscussionsMapper bookDiscussionsMapper;
    @Autowired
    private BdUserService bdUserService;
    @Autowired
    private UserBannedService userBannedService;

    /**
     * 创建书圈
     *
     * @param bookDiscussionsPojo
     * @return
     */
    @Override
    public MyResult addBookDiscussion(BookDiscussionsPojo bookDiscussionsPojo) {
        if (bookDiscussionsMapper.addBookDiscussion(bookDiscussionsPojo)) {
            return MyResult.success(bookDiscussionsPojo, "成功创建书圈");
        } else {
            return MyResult.failed("创建书圈失败", null, 508);
        }
    }

    /**
     * 查询书圈信息
     *
     * @param param
     * @return
     */
    @Override
    public PageResult queryDiscussion(PageRequestParam param) {
        return PageUtils.getPageResult(param, getPageInfo(param));
    }

    /**
     * 根据id查询书圈
     *
     * @param discussionId
     * @return
     */
    @Override
    public MyResult queryDiscussionById(int userId, int discussionId) {
        BookDiscussionsPojo bookDiscussionsPojo = bookDiscussionsMapper.queryBookDiscussionById(discussionId);
        HashMap<String, Object> result = new HashMap<>();
        if (bookDiscussionsPojo != null) {
            result.put("state", bdUserService.stateByJoin(userId, discussionId));
            result.put("num", bdUserService.queryNums(discussionId));
            result.put("isBanned", userBannedService.isUserBanned(userId));
            result.put("obj", bookDiscussionsPojo);
            result.put("myId", userId);
            return MyResult.success(result, "查询成功");
        } else {
            return MyResult.failed("查询失败", null, 508);
        }
    }

    /**
     * 删除书圈
     * @param discussionId 书圈id
     * @return 自定义返回类型
     */
    @Override
    public MyResult deleteDiscussion(int discussionId) {
        if (bookDiscussionsMapper.deleteBookDiscussion(discussionId)){
            return MyResult.success(true,"删除成功");
        }else {
            return MyResult.failed("删除失败",false,508);
        }
    }

    /**
     * 调用分页插件完成分页
     *
     * @param param
     * @return
     */
    private PageInfo<BookDiscussionsPojo> getPageInfo(PageRequestParam param) {
        PageHelper.startPage(param.getPageNumber(), param.getPageSize());
        List<BookDiscussionsPojo> discussions = bookDiscussionsMapper.selectPage();
        return new PageInfo<BookDiscussionsPojo>(discussions);
    }
}
