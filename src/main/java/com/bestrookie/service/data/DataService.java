package com.bestrookie.service.data;

import com.bestrookie.model.MyResult;


/**
 * @author : bestrookie
 * @date : 9:38 2020/11/18
 */
public interface DataService {
    /**
     * 用户书籍数据
     * @param userId 用户id
     * @return 书籍数据
     */
    MyResult userBookData(int userId);

    /**
     * 书籍数据分析
     * @return 自定义返回类型
     */
    MyResult bookData();

    /**
     * 书籍类型分析
     * @return 自定义返回类型
     */
    MyResult bookTypeData();

    /**
     * 书圈数量
     * @return 自定义返回类型
     */
    MyResult bookDiscussionData();
}
