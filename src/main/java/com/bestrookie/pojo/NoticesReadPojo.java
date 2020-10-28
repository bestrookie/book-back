package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 16:13 2020/10/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticesReadPojo {
    private int nrId;
    private long nrDate;
    private int userId;
    private int noticeId;
}
