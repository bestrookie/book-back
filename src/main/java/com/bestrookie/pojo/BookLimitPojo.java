package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 14:34 2020/11/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookLimitPojo {
    private int limitId;
    private int userId;
    private int bookId;
    private long limitDate;
    private int limitPage;
    private int nowPage;
    private boolean limitAll;
    private BookPojo book;
}
