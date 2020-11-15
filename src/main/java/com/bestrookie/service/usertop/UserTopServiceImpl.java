package com.bestrookie.service.usertop;

import com.bestrookie.mapper.UserTopMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.UserTopPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 16:52 2020/11/14
 */
@Service
public class UserTopServiceImpl implements UserTopService{
    @Autowired
    private UserTopMapper topMapper;
    @Override
    public boolean isTopExist(int userId) {
        return topMapper.isTopExist(userId) != 0;
    }

    @Override
    public void addUserTopInfo(UserTopPojo userTopPojo) {
        topMapper.addUserTop(userTopPojo);
    }

    @Override
    public void updateUserTop(int userId) {
        topMapper.updateUserTop(userId);
    }

    @Override
    public MyResult queryUserTop() {
        List<UserTopPojo> tops = topMapper.queryUserTop();
        if (tops != null){
            return MyResult.success(tops);
        }else {
            return MyResult.failed("查询失败",null,527);
        }
    }
}
