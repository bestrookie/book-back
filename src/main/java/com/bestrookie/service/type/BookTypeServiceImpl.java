package com.bestrookie.service.type;

import com.bestrookie.mapper.BookTypeMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookTypePojo;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 19:42 2020/11/7
 */
@Service
public class BookTypeServiceImpl implements BookTypeService{
    @Autowired
    private BookTypeMapper bookTypeMapper;
    /**
     * 查询书籍类型
     * @return 自定义返回类型
     */
    @Override
    public MyResult queryBookType() {
        List<BookTypePojo> bookTypes = bookTypeMapper.queryBookType();
        if (bookTypes != null){
            return MyResult.success(bookTypes,"查询成功");
        }else {
            return  MyResult.failed("查询失败",null,518);
        }
    }

    /**
     * 分页查看书籍类型
     * @param param 分页参数
     * @return 分页结果
     */
    @Override
    public PageResult queryAllType(PageRequestParam param) {
        return PageUtils.getPageResult(param,getTypeInfo(param));
    }

    /**
     * 添加书籍类型
     * @param bookTypePojo 书籍类型实体
     * @return 自定义返回类型
     */
    @Override
    public MyResult addBookType(BookTypePojo bookTypePojo) {
        if (bookTypeMapper.addBookType(bookTypePojo)){
            return MyResult.success(true);
        }else {
            return MyResult.failed("添加失败",false,518);
        }
    }

    /**
     * 修改书籍类型
     * @param bookTypePojo 书籍类型实体
     * @return 自定义返回类型
     */
    @Override
    public MyResult updateBookType(BookTypePojo bookTypePojo) {
        if (bookTypeMapper.updateBookType(bookTypePojo)){
            return MyResult.success(true);
        }else {
            return MyResult.failed("修改失败",false,518);
        }
    }

    /**
     * 调用分页工具
     * @param param 分页参数
     * @return 分页结果
     */
    private PageInfo<BookTypePojo> getTypeInfo(PageRequestParam param){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<BookTypePojo> bookTypes = bookTypeMapper.queryBookType();
        return  new PageInfo<>(bookTypes);
    }
}
