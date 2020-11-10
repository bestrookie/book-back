package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.CollectionPojo;
import com.bestrookie.service.collection.CollectionService;
import com.bestrookie.utils.IsTrueUtils;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : bestrookie
 * @date : 20:39 2020/11/10
 */
@RestController
@RequestMapping("api/collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;
    @GetMapping("/on")
    public MyResult onCollection(HttpServletRequest request, HttpServletResponse response){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("bookId"))){
            CollectionPojo collectionPojo = new CollectionPojo();
            collectionPojo.setBookId(Integer.parseInt(request.getParameter("bookId")));
            collectionPojo.setCollectionDate(System.currentTimeMillis());
            collectionPojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
            result = collectionService.addCollection(collectionPojo);
        }else {
            result = MyResult.failed("参数错误",false,412);
        }
        response.setStatus(result.getCode());
        return result;
    }
    @GetMapping("/off")
    public MyResult offCollection(HttpServletResponse response ,HttpServletRequest request){
        MyResult result;
        if (IsTrueUtils.isTrue(request.getParameter("bookId"))){
            result = collectionService.cancelCollection(TokenUtils.getId(request.getHeader("authorization")), Integer.parseInt(request.getParameter("bookId")));
        }else {
            result = MyResult.failed("参数错误",false,412);
        }
        response.setStatus(result.getCode());
        return result;
    }
}
