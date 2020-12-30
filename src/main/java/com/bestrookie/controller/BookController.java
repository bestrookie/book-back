package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookPojo;
import com.bestrookie.service.async.AsyncService;
import com.bestrookie.service.books.BookService;
import com.bestrookie.service.recommend.RecommendBookService;
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
 * @date : 19:27 2020/11/7
 */
@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private RecommendBookService recommendBookService;
    @Value("${file.banWord-path}")
    private String wordPath;
    /**
     * 上传书籍
     * @param response 响应参数
     * @param bookPojo 书籍信息
     * @param request 请求参数
     * @return 自定义返回类型
     */
    @SneakyThrows
    @PostMapping("uploadbook")
    public MyResult uploadBook(HttpServletRequest request,HttpServletResponse response, @RequestBody BookPojo bookPojo){
        MyResult result;
        SensitiveWordUtils.init(wordPath);
        if (!(SensitiveWordUtils.contains(bookPojo.getBookName()) && SensitiveWordUtils.contains(bookPojo.getPublisher()) && SensitiveWordUtils.contains(bookPojo.getAuthor()))){
            bookPojo.setUploadDate(System.currentTimeMillis());
            bookPojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
            result = bookService.uploadBook(bookPojo);
            asyncService.isBookLegal(bookPojo);
        }else {
            result = MyResult.failed("参数错误",null,412);
        }

        response.setStatus(result.getCode());
        return result;
    }
    /**
     * 查询pdf是否存在
     * @param response 响应参数
     * @param request 请求参数
     * @return 自定义返回类型
     */
    @GetMapping("isexist")
    public MyResult isPdfExists(HttpServletResponse response , HttpServletRequest request){
        MyResult result;
        result = bookService.queryPdfExists(request.getParameter("identity"));
        response.setStatus(result.getCode());
        return result;
    }
    /**
     * 查询书籍详情
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("querybookinfo")
    public MyResult queryBookInfo(HttpServletRequest request ,HttpServletResponse response){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("bookId"))){
            result = bookService.queryBookById(Integer.parseInt(request.getParameter("bookId")),TokenUtils.getId(request.getHeader("authorization")));
        }else {
            result = MyResult.failed("参数错误",null,412);
        }
        response.setStatus(result.getCode());
        return result;
    }
    /**
     * 我的上传
     * @param response 响应参数
     * @param request 请求参数
     * @return 自定义返回值
     */
    @GetMapping("/queryupload")
    public MyResult queryMyUpload(HttpServletResponse response,HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))){
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = bookService.queryMyUpload(param, TokenUtils.getId(request.getHeader("authorization")));
            if (pageResult == null) {
                result = MyResult.failed("查看上传历史失败", null, 519);
            } else {
                result = MyResult.success(pageResult);
            }
        }else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return  result;
    }
    /**
     * 查看书架
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义返回值
     */
    @GetMapping("/mycollection")
    public MyResult queryMyCollection(HttpServletRequest request,HttpServletResponse response){
        MyResult result;
        result = bookService.queryMyCollect(TokenUtils.getId(request.getHeader("authorization")));
        response.setStatus(result.getCode());
        return  result;
    }
    /**
     * 全局搜索
     * @param response 请求参数
     * @param request 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/fuzzy")
    public MyResult queryFuzzy(HttpServletResponse response,HttpServletRequest request,@RequestParam String key,@RequestParam String type){
        MyResult result;
        if (!IsTrueUtils.isTrue(key)){
            int typeId = 0;
            if (!"undefined".equals(type)){
                typeId = Integer.parseInt(type);
            }
            if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))){
                PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                        Integer.parseInt(request.getParameter("pageSize")));
                PageResult pageResult = bookService.queryFuzzy(param,key,typeId);
                if (pageResult == null) {
                    result = MyResult.failed("搜索失败", null, 524);
                } else {
                    result = MyResult.success(pageResult);
                }
            }else {
                result = MyResult.failed("参数错误", null, 412);
            }
        }else {
            if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
                PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                        Integer.parseInt(request.getParameter("pageSize")));
                PageResult pageResult = bookService.queryById(param, Integer.parseInt(key));
                if (pageResult == null) {
                    result = MyResult.failed("搜索失败", null, 524);
                } else {
                    result = MyResult.success(pageResult);
                }
            } else {
                result = MyResult.failed("参数错误", null, 412);
            }
        }

        response.setStatus(result.getCode());
        return  result;
    }

    /**
     * 分类榜
     * @param request 请求参数
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/typetop")
    public MyResult queryTypeTop(HttpServletRequest request,HttpServletResponse response){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("typeId"))){
            result = bookService.booksOutByType(Integer.parseInt(request.getParameter("typeId")));
        }else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return  result;
    }

    /**
     * 查看未过审书籍
     * @param response 请求参数
     * @param request 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/unbook")
    public MyResult queryUnBook(HttpServletResponse response,HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("pageNumber")) && IsTrueUtils.isTrue(request.getParameter("pageSize"))) {
            PageRequestParam param = new PageRequestParam(Integer.parseInt(request.getParameter("pageNumber")),
                    Integer.parseInt(request.getParameter("pageSize")));
            PageResult pageResult = bookService.queryUnBook(param);
            if (pageResult == null) {
                result = MyResult.failed("查看失败", null, 519);
            } else {
                result = MyResult.success(pageResult);
            }
        }else {
            result = MyResult.failed("参数错误", null, 412);
        }
        response.setStatus(result.getCode());
        return  result;
    }

    /**
     * 查看书籍排行榜
     * @param response 响应参数
     * @return 自定义返回类型
     */
    @GetMapping("/top")
    public MyResult queryTop(HttpServletResponse response){
        MyResult result;
        result = bookService.queryTop();
        response.setStatus(result.getCode());
        return  result;
    }

    /**
     * 书籍推荐
     * @param request  请求参数
     * @param response 响应参数
     * @param bookId 书籍id
     * @return 自定义返回类型
     */
    @GetMapping("/recommend")
    public MyResult getRecommend(HttpServletRequest request,HttpServletResponse response,@RequestParam int bookId){
        MyResult result = recommendBookService.recommendBook(bookId,TokenUtils.getId(request.getHeader("authorization")));
        response.setStatus(result.getCode());
        return  result;
    }


}
