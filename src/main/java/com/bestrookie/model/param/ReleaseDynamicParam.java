package com.bestrookie.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 19:13 2020/10/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseDynamicParam {
    private int bdId;
    private String content;
    private String abstracts;
}
