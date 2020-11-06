package com.bestrookie.mapper;

import com.bestrookie.pojo.DynamicPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author : bestrookie
 * @date : 19:59 2020/10/15
 */
@Repository
@Mapper
public interface DynamicMapper {
    /**
     * 分页查询书圈的动态
     * @param discussionId 书圈id
     * @return 动态列表
     */
    List<DynamicPojo> queryDynamic(int discussionId);
    /**
     * 添加动态信息
     * @param dynamicPojo 动态实体
     * @return 是否添加成功
     */
    boolean addDynamic(DynamicPojo dynamicPojo);
    /**
     * 根据id查询动态
     * @param dynamicId 动态id
     * @return 动态信息
             */
    DynamicPojo queryDynamicById(int dynamicId);
    /**
     * 根据id删除动态
     * @param dynamicId 动态id
     * @param userId 用户id
     * @return 是否删除成功
     */
    boolean deleteDynamicById(@Param("dynamicId") int dynamicId, @Param("userId") int userId);

    /**
     * 管理院删除动态
     * @param dynamicId
     * @return
     */
    boolean deleteDynamic(@Param(value = "dynamicId") int dynamicId);

    /**
     * 查询所有动态
     * @return
     */
    List<DynamicPojo> queryAllDynamic();

}
