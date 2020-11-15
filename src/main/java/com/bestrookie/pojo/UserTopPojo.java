package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 15:04 2020/11/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTopPojo {
    private int topId;
    private int userId;
    private long topDate;
    private int topCount;
    private UserPojo user;
}
