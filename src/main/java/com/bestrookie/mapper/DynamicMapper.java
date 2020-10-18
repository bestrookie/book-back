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
     * @param discussionId
     * @return
     */
    List<DynamicPojo> queryDynamic(int discussionId);
    /**
     * 添加动态信息
     * @param dynamicPojo
     * @return
     */
    boolean addDynamic(DynamicPojo dynamicPojo);
    /**
     * 根据id查询动态
     * @param dynamicId
     * @return
             */
    DynamicPojo queryDynamicById(int dynamicId);
    /**
     * 根据id删除动态
     * @param dynamicId
     * @param userId
     * @return
     */
    boolean deleteDynamicById(@Param("dynamicId") int dynamicId, @Param("userId") int userId);

}
