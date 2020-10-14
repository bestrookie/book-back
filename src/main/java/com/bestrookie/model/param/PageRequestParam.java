package com.bestrookie.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 19:17 2020/10/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestParam {
    private int pageNumber;
    private int pageSize;
}
