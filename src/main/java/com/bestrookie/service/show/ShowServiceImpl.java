package com.bestrookie.service.show;

import com.bestrookie.mapper.ShowMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.ShowPojo;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 10:48 2020/11/11
 */
@Service
public class ShowServiceImpl implements ShowService {
    @Autowired
    private ShowMapper showMapper;
    /** 分页查看所有展示信息
     * @param param 分页参数
     * @return 分页结果
     */
    @Override
    public PageResult queryShowInfo(PageRequestParam param) {
        return PageUtils.getPageResult(param,getShowInfo(param));
    }

    /**
     * 查询展示信息
     * @return 展示信息
     */
    @Override
    public MyResult queryShow() {
        List<ShowPojo> shows = showMapper.queryShow();
        if (shows == null){
            return MyResult.success(null,"暂无展示信息");
        }else {
            return MyResult.success(shows);
        }
    }

    /**
     * 修改展示状态
     * @param showId 展示信息id
     * @param state 状态
     * @return 自定义返回类型
     */
    @Override
    public MyResult updateShowState(int showId, boolean state) {
        if (showMapper.updateShowState(showId, state)){
            return MyResult.success(true);
        }else {
            return MyResult.failed("修改失败",false,523);
        }
    }

    /**
     * 删除展示信息
     * @param showId 展示id
     * @return 自定义返回类型
     */
    @Override
    public MyResult deleteShow(int showId) {
        if (showMapper.deleteShowInfo(showId)){
            return MyResult.success(true);
        }else {
            return MyResult.failed("删除失败",false,523);
        }
    }

    /**
     * 添加展示信息
     * @param showPojo 展示信息
     * @return 自定义返回类型
     */
    @Override
    public MyResult addShow(ShowPojo showPojo) {
        if (showMapper.addShow(showPojo)) {
            return MyResult.success(true);
        }else {
            return MyResult.failed("添加失败",false,523);
        }
    }

    /**
     * 查询展示的信息数量
     * @return 数量
     */
    @Override
    public int showCount() {
        return showMapper.showCount();
    }

    private PageInfo<ShowPojo> getShowInfo(PageRequestParam param){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<ShowPojo> shows = showMapper.queryShowInfo();
        return new PageInfo<>(shows);
    }
}
