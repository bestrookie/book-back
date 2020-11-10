package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author : bestrookie
 * @date : 19:34 2020/11/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionPojo {
    private int collectionId;
    private long collectionDate;
    private int userId;
    private int bookId;
}
