package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 9:21 2020/11/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookReviewPojo {
    private int reviewId;
    private String reviewContent;
    private long reviewDate;
    private int value;
    private int bookId;
    private int userId;
    private UserPojo user;
}
