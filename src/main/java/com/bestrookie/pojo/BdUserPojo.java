package com.bestrookie.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 20:23 2020/10/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BdUserPojo {
    private int bdUserId;
    private long bduDate;
    private int userId;
    private int bdId;
}
