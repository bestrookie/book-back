package com.bestrookie.service.adminlog;

import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.LogPojo;

/**
 * @author : bestrookie
 * @date : 20:10 2020/11/2
 */
public interface AdminLogService {
    /**添加日志信息
     * @param logPojo 日志信息实体
     * @return 谁否添加成功
     */
    boolean addAdminLog(LogPojo logPojo);

    /**
     * 查询日志信息
     * @param param 分页参数
     * @return 自定义返回类型
     */
    PageResult queryLogInfo(PageRequestParam param);
}
