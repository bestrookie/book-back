package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.AddDiscussionParam;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookDiscussionsPojo;
import com.bestrookie.service.bookdiscussion.BookDiscussionsService;
import com.bestrookie.utils.IsTrueUtils;
import com.bestrookie.utils.SensitiveWordUtils;
import com.bestrookie.utils.TokenUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 16:33 2020/10/13
 */
@RestController
@RequestMapping("/api/discussions")
public class BookDiscussionsController {
    @Autowired
    private BookDiscussionsService bookDiscussionsService;
    @Value("${file.banWord-path}")
    private String wordPath;

    /**
     * 创建书圈
     * @param response 响应参数
     * @param request  请求参数
     * @param param    分页参数
     * @return 自定义返回类型
     */
    @SneakyThrows
    @PostMapping("/adddiscussion")
    public MyResult addDiscussion(HttpServletResponse response, HttpServletRequest request,@RequestBody AddDiscussionParam param) {
        MyResult result;
        SensitiveWordUtils.init(wordPath);
        if (!(SensitiveWordUtils.contains(param.getDbName()) && SensitiveWordUtils.contains(param.getDbDes()))) {
            BookDiscussionsPojo bookDiscussionsPojo = new BookDiscussionsPojo();
            bookDiscussionsPojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
            bookDiscussionsPojo.setBdName(param.getDbName());
            bookDiscussionsPojo.setBdDes(param.getDbDes());
            bookDiscussionsPojo.setBdDate(System.currentTimeMillis());
            bookDiscussionsPojo.setBookId(param.getBookId());
            bookDiscussionsPojo.setBdPhoto(param.getDiscussionCover());
            result = bookDiscussionsService.addBookDiscussion(bookDiscussionsPojo);
        } else {
            result = MyResult.failed("输入信息包含敏感词汇", null, 408);
        }
        response.setStatus(result.getCode());
        return result;
    }

    /**
     * 分页查找书圈信息
     * @param request  请求参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/querydiscussion")
    public MyResult queryBookDiscussions(HttpServletRequest request, HttpServletResponse response) {
        MyResult myResult;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult result = bookDiscussionsService.queryDiscussion(param);
            if (result.getContent() == null) {
                myResult = MyResult.failed("查询书圈失败", null, 507);
            } else {
                myResult = MyResult.success(result);
            }
        } else {
            myResult = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(myResult.getCode());
        return myResult;
    }

    /**
     * 根据id查询书圈
     * @param response 响应参数
     * @param request  请求参数
     * @return 自定义返回类型
     */
    @GetMapping("/querybyid")
    public MyResult queryBookDiscussionById(HttpServletResponse response, HttpServletRequest request) {
        MyResult result;
        int userId = TokenUtils.getId(request.getHeader("authorization"));
        if (IsTrueUtils.isTrue(request.getParameter("discussionId"))) {
            int discussionId = Integer.parseInt(request.getParameter("discussionId"));
            result = bookDiscussionsService.queryDiscussionById(userId, discussionId);
        } else {
            result = MyResult.failed("参数错误", null, 412);
        }

        response.setStatus(result.getCode());
        return result;
    }
}
