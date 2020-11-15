package com.bestrookie.mapper;

import com.bestrookie.pojo.UserTopPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 15:07 2020/11/14
 */
@Repository
@Mapper
public interface UserTopMapper {
    /**
     * 查询是否存在排行信息
     * @param userId 用户id
     * @return 是否存在
     */
    int isTopExist(@Param("userId") int userId);

    /**
     * 添加排行信息
     * @param userTopPojo 排行信息
     * @return 自定义返回类型
     */
    boolean addUserTop(UserTopPojo userTopPojo);

    /**
     * 更新上传数量
     * @param userId 用户id
     * @return 是否成功
     */
    boolean updateUserTop(@Param(value = "userId") int userId);
    /**
     * 查询用户排行
     * @return 排行信息
     */
    List<UserTopPojo> queryUserTop();


}
