package com.bestrookie.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 18:14 2020/10/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDiscussionParam {
    private String dbName;
    private String dbDesc;
}
