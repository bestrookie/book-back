package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : bestrookie
 * @date : 10:01 2020/10/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicReviewPojo {
    private int drId;
    private String drContent;
    private long drDate;
    private int drPid;
    private int drSpid;
    private int userId;
    private int dynamicId;
    private UserPojo userPojo;
    private UserPojo repUserPojo;
    private List<DynamicReviewPojo> sonReview;
}
