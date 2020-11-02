package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 19:51 2020/10/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogPojo {
    private int logId;
    private long logDate;
    private String logOperate;
    private String logIp;
    private int userId;
}
