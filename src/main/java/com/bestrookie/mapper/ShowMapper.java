package com.bestrookie.mapper;

import com.bestrookie.pojo.ShowPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 10:19 2020/11/11
 */
@Repository
@Mapper
public interface ShowMapper {
    /**
     * 添加展示项目
     *
     * @param showPojo 展示信息
     * @return 是否添加成功
     */
    boolean addShow(ShowPojo showPojo);

    /**
     * 查看展示信息
     *
     * @return 展示信息列表
     */
    List<ShowPojo> queryShowInfo();

    /**
     * 展示
     *
     * @return 展示列表
     */
    List<ShowPojo> queryShow();

    /**
     * 修改展示状态
     *
     * @param showId 展示信息id
     * @param state  状态
     * @return 是否修改成功
     */
    boolean updateShowState(@Param(value = "showId") int showId, @Param(value = "state") boolean state);

    /**
     * 删除展示信息
     *
     * @param showId 展示信息id
     * @return 是否删除成功
     */
    boolean deleteShowInfo(@Param(value = "showId") int showId);

    /**
     * 查询展示的信息数量
     *
     * @return 数量
     */
    int showCount();

}
