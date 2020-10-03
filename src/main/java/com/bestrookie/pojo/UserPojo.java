package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 10:00 2020/10/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPojo {
    private int userId;
    private String userName;
    private String password;
    private String image;
    private int userCoin;
    private int role;
}
