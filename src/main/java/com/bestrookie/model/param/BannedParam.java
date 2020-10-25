package com.bestrookie.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 16:29 2020/10/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannedParam {
    private int userId;
    private String bannedDes;
    private int bannedType;
    private boolean custom;
    private long time;
}
