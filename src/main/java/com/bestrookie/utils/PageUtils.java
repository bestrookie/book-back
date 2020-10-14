package com.bestrookie.utils;

import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.github.pagehelper.PageInfo;

/**
 * @author : bestrookie
 * @date : 20:04 2020/10/13
 */
public class PageUtils {
    public static PageResult getPageResult(PageRequestParam param, PageInfo<?> pageInfo){
        PageResult result = new PageResult();
        result.setPageNumber(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotalSize(pageInfo.getTotal());
        result.setTotalPages(pageInfo.getPages());
        result.setContent(pageInfo.getList());
        return  result;
    }
}
