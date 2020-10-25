package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 20:47 2020/10/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBannedPojo {
    private  int bannedId;
    private long bannedDate;
    private long removeDate;
    private String bannedDes;
    private int userId;
    private UserPojo userPojo;
}
