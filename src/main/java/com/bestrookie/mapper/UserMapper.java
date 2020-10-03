package com.bestrookie.mapper;

import com.bestrookie.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : bestrookie
 * @date : 10:09 2020/10/3
 */
@Repository
@Mapper
public interface UserMapper {
    public UserPojo queryUserByName(String name);
}
