package com.bestrookie.mapper;

import com.bestrookie.pojo.ReportPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 9:46 2020/10/29
 */
@Mapper
@Repository
public interface ReportMapper {
    /**
     * 添加举报信息
     * @param reportPojo
     * @return
     */
    boolean addReportInfo(ReportPojo reportPojo);
    /**
     * 查看所有举报信息
     * @return
     */
    List<ReportPojo> queryReportInfo();
    /**
     * 改变举报信息状态
     * @param reportId
     * @return
     */
    boolean solveReport(int reportId);
    /**
     * 为解决举报数量
     * @return
     */
    int unSolveReport();
    /**
     * 根据类型查看举报信息
     * @param type
     * @return
     */
    List<ReportPojo> queryReportInfoByType(int type);

}
