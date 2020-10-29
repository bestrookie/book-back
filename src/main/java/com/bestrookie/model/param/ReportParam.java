package com.bestrookie.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 10:56 2020/10/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportParam {
    private int targetId;
    private String description;
    private int reportType;
}
