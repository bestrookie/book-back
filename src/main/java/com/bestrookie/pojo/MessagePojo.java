package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 15:06 2020/10/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagePojo {
    private int msgId;
    private String msg;
    private long msgDate;
    private boolean read = false;
    private int type;
    private boolean flg =false;
    private int targetId;
    private int userId;
    private int dynamicId;
    private String userName;
    private String avatarUrl;
    private String token;
}
