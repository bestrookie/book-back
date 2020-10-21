package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.model.param.ReleaseDynamicReviewParam;
import com.bestrookie.pojo.DynamicReviewPojo;
import com.bestrookie.service.dynamicReview.DynamicReviewService;
import com.bestrookie.utils.IsTrueUtils;
import com.bestrookie.utils.SensitiveWordUtils;
import com.bestrookie.utils.TokenUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wltea.analyzer.core.IKSegmenter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 16:06 2020/10/19
 */
@RestController
@RequestMapping("/api/dynamicreview")
public class DynamicReviewController {
    @Autowired
    private DynamicReviewService dynamicReviewService;
    /**
     * 查看评论
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/queryreviews")
    public MyResult getDynamicReview(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize")) && IsTrueUtils.isTrue(request.getParameter("dId"))){
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = dynamicReviewService.queryDynamicReview(param, Integer.parseInt(request.getParameter("dId")));
            if (pageResult == null){
                result = MyResult.failed("查询评论失败",null,511);
            }else {
                int reviewNums = dynamicReviewService.reviewCount(Integer.parseInt(request.getParameter("dId")));
                pageResult.setTotalSize(reviewNums);
                result = MyResult.success(pageResult);
            }
        }else {
            result = MyResult.failed("参数错误",null,412);
        }

        response.setStatus(result.getCode());
        return result;
    }
    /**
     * 删除评论
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/deletereview")
    public MyResult deleteDynamicReview(HttpServletResponse response,HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("dId")) && IsTrueUtils.isTrue(request.getParameter("reviewId"))){
            int dynamicId = Integer.parseInt(request.getParameter("dId"));
            int reviewId = Integer.parseInt(request.getParameter("reviewId"));
            int userId = TokenUtils.getId(request.getHeader("authorization"));
            result = dynamicReviewService.deleteDynamicReview(dynamicId,reviewId,userId);
        }else {
            result = MyResult.failed("参数错误",null,412);
        }

        response.setStatus(result.getCode());
        return result;
    }
    /**
     * 发布评论
     * @param request
     * @param response
     * @param param
     * @return
     */
    @SneakyThrows
    @PostMapping("/releasereview")
    public MyResult releaseDynamicReview(HttpServletRequest request, HttpServletResponse response, @RequestBody ReleaseDynamicReviewParam param){
        MyResult result;
        if (param.getReviewContent() == null){
            result = MyResult.failed("参数错误",false,411);
        }else {
            SensitiveWordUtils.init();
            if (SensitiveWordUtils.contains(param.getReviewContent())){
                result = MyResult.failed("内容违规",false,411);
            }else {
                DynamicReviewPojo dynamicReviewPojo = new DynamicReviewPojo();
                dynamicReviewPojo.setDrContent(param.getReviewContent());
                dynamicReviewPojo.setDrDate(System.currentTimeMillis());
                dynamicReviewPojo.setDrPid(param.getParentId());
                dynamicReviewPojo.setDrSpid(param.getSupParentId());
                dynamicReviewPojo.setDynamicId(param.getDynamicId());
                dynamicReviewPojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
                result = dynamicReviewService.releaseDynamicReview(dynamicReviewPojo);
            }
        }
        response.setStatus(result.getCode());
        return result;
    }
}
