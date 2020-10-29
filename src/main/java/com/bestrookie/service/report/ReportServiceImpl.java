package com.bestrookie.service.report;

import com.bestrookie.mapper.ReportMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.model.PageResult;
import com.bestrookie.model.param.PageRequestParam;
import com.bestrookie.pojo.ReportPojo;
import com.bestrookie.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 10:22 2020/10/29
 */
@Service
public class ReportServiceImpl implements ReportService{
    @Autowired
    private ReportMapper reportMapper;

    /**
     * 查询所有举报信息
     * @param param 分页参数
     * @return 分页结果
     */
    @Override
    public PageResult queryReports(PageRequestParam param,int type) {
        return PageUtils.getPageResult(param,getReportInfo(param,type));
    }

    /**
     * 添加举报信息
     * @param reportPojo 举报实体
     * @return 自定义返回类型
     */
    @Override
    public MyResult addReportInfo(ReportPojo reportPojo) {
        if (reportMapper.addReportInfo(reportPojo)){
            return MyResult.success(true);
        }else {
            return MyResult.failed("举报失败",false,516);
        }
    }

    /**
     * 处理举报信息
     * @param reportId
     * @return 自定义返回类型
     */
    @Override
    public MyResult solveReport(int reportId) {
        if (reportMapper.solveReport(reportId)){
            return MyResult.success(true,"已处理");
        }else {
            return MyResult.failed("处理失败",false,516);
        }
    }

    /**
     * 调用分页插件
     * @param param
     * @return
     */
    private PageInfo<ReportPojo> getReportInfo(PageRequestParam param,int type){
        PageHelper.startPage(param.getPageNumber(),param.getPageSize());
        List<ReportPojo> reports = reportMapper.queryReportInfo(type);
        return new PageInfo<>(reports);
    }
}
