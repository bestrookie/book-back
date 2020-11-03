package com.bestrookie.service.adminlog;

import com.bestrookie.mapper.LogMapper;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.LogPojo;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 20:14 2020/11/2
 */
@Service
public class AdminLogServiceImpl implements AdminLogService {
    @Autowired
    private LogMapper logMapper;

    /**
     * 添加日志信息
     * @param logPojo 日志信息实体
     * @return 是否添加成功
     */
    @Override
    public boolean addAdminLog(LogPojo logPojo) {
        return logMapper.addLogInfo(logPojo);
    }
    /**
     * 查询日志信息
     * @param param 分页参数
     * @return 分页结果
     */
    @Override
    public PageResult queryLogInfo(PageRequestParam param) {
        return PageUtils.getPageResult(param,getLogInfo(param));
    }
    /**
     * 调用分页插件分页
     * @param param 分页参数
     * @return 分页结果
     */
    private PageInfo<LogPojo> getLogInfo(PageRequestParam param){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<LogPojo> logs = logMapper.queryLogInfo();
        return new PageInfo<>(logs);
    }
}
