package com.bestrookie.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 10:15 2020/10/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseNoticeParam {
    private String noticeContent;
    private String noticeTitle;
}
