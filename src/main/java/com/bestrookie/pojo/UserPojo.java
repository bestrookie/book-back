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
    private int userCoin= 0;
    private int role = 0;
    private String userPhone;
    private UserBannedPojo bannedPojo;

    public UserPojo(String userName, String password, String image,String userPhone) {
        this.userName = userName;
        this.password = password;
        this.image = image;
        this.userPhone = userPhone;
    }

    public UserPojo(String userName, String password, String image) {
        this.userName = userName;
        this.password = password;
        this.image = image;
    }
}
