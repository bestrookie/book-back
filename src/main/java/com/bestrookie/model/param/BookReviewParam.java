package com.bestrookie.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 10:16 2020/11/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookReviewParam {
    private String reviewContent;
    private int value;
    private int bookId;
}
