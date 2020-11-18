package com.bestrookie.service.data;

import com.bestrookie.mapper.BookDiscussionsMapper;
import com.bestrookie.mapper.BookHistoryMapper;
import com.bestrookie.mapper.BookMapper;
import com.bestrookie.mapper.BookTypeMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.BookHistoryPojo;
import com.bestrookie.pojo.BookTypePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 9:41 2020/11/18
 */
@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private BookTypeMapper typeMapper;
    @Autowired
    private BookHistoryMapper historyMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookDiscussionsMapper discussionsMapper;

    /**
     * 统计用户书籍数据
     * @param userId 用户id
     * @return 数据列表
     */
    @Override
    public MyResult userBookData(int userId) {
        List<BookTypePojo> typeList = typeMapper.queryBookType();
        List<BookHistoryPojo> historyBook = historyMapper.queryBookHistoryById(userId);
        int [] data = new int[typeList.size()];
        for (BookHistoryPojo history : historyBook) {
            data[history.getBook().getType() -1] += 1;
        }
        return MyResult.success(data);
    }

    @Override
    public MyResult bookData() {
        List<BookTypePojo> types = typeMapper.queryBookType();
        List<BookHistoryPojo> bookHistory = historyMapper.querAllHistory();
        int [] data = new int[types.size()];
        for (BookHistoryPojo history : bookHistory) {
            data[history.getBook().getType() -1 ] +=1;
        }
        return MyResult.success(data);
    }

    @Override
    public MyResult bookTypeData() {
        List<BookTypePojo> types = typeMapper.queryBookType();
        int []data = new int[types.size()];
        for (BookTypePojo type : types) {
            data[type.getTypeId()-1] = bookMapper.queryNumByType(type.getTypeId());
        }
        return MyResult.success(data);
    }

    @Override
    public MyResult bookDiscussionData() {
        return MyResult.success(discussionsMapper.discussionNum());
    }
}
