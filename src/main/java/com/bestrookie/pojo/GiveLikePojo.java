package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 20:10 2020/10/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiveLikePojo {
    private int likeId;
    private long likeDate;
    private int userId;
    private int dynamicId;
}
