package com.bestrookie.service.report;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.ReportPojo;

/**
 * @author : bestrookie
 * @date : 10:21 2020/10/29
 */
public interface ReportService {
    /**
     * 分页查询所有的举报信息
     * @param param 分页参数
     * @return 分页结果
     */
    PageResult queryReports(PageRequestParam param,int type);

    /**
     * 举报违法信息
     * @param reportPojo 举报实体
     * @return 自定义结果集
     */
    MyResult addReportInfo(ReportPojo reportPojo);
    /**
     * 已处理举报信息
     * @param reportId
     * @return
     */
    MyResult solveReport(int reportId);

    /**
     * 未处理举报数量
     * @return
     */
    int unSolveReport();
}
