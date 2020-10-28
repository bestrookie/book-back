package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 10:48 2020/10/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticePojo {
    private int noticeId;
    private long noticeDate;
    private String noticeContent;
    private String noticeTitle;
    private int nums;
    private boolean read = false;
}
