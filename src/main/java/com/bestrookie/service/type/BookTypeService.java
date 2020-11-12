package com.bestrookie.service.type;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.BookTypePojo;

/**
 * @author : bestrookie
 * @date : 19:41 2020/11/7
 */
public interface BookTypeService {
    /**
     * 查询书籍类型
     * @return 自定义返回类型
     */
    MyResult queryBookType();

    /**
     * 分页查看书籍类型
     * @param param 分页参数
     * @return 分页结果
     */
    PageResult queryAllType(PageRequestParam param);

    /**
     * 添加书籍类型
     * @param bookTypePojo 书记类型实体
     * @return 自定义返回类型
     */
    MyResult addBookType(BookTypePojo bookTypePojo);

    /**
     * 修改书籍类型
     * @param bookTypePojo 书籍类型实体
     * @return 自定义返回类型
     */
    MyResult updateBookType(BookTypePojo bookTypePojo);
}
