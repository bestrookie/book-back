package com.bestrookie.service.show;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.ShowPojo;


/**
 * @author : bestrookie
 * @date : 10:42 2020/11/11
 */
public interface ShowService {
    /**
     * 分页查询展示信息
     * @param param 分页参数
     * @return 分页结果
     */
    PageResult queryShowInfo(PageRequestParam param);

    /**
     * 查询展示信息
     * @return 展示信息列表
     */
    MyResult queryShow();
    /**
     * 更改展示状态
     * @param showId 展示信息id
     * @param state 状态
     * @return 自定义返回类型
     */
    MyResult updateShowState(int showId,boolean state);

    /**
     * 删除展示信息
     * @param showId 展示id
     * @return 自定义返回类型
     */
    MyResult deleteShow(int showId);

    /**
     * 添加展示信息
     * @param showPojo 展示信息
     * @return 自定义返回类型
     */
    MyResult addShow(ShowPojo showPojo);

    /**
     * 查询展示信息的数量
     * @return 数量
     */
    int showCount();
}
