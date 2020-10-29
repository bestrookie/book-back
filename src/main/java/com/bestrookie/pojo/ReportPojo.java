package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 20:55 2020/10/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportPojo {
    private int reportId;
    private long reportDate;
    private String reportDes;
    private int userId;
    private int targetId;
    private int reportType;
    private int reportState;
    public static final String[] reportTypeName = {"动态","书圈","书籍"};
    public String getReportTypeName() {
        return reportTypeName[reportType];
    }
}
