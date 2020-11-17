package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 19:40 2020/11/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookHistoryPojo {
    private int historyId;
    private int userId;
    private int bookId;
    private long historyDate;
    private int historyPage;
    private BookPojo book;
}
