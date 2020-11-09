package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 18:39 2020/11/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookPojo {
    private int bookId;
    private String bookName;
    private String author;
    private String publisher;
    private long publishDate;
    private long uploadDate;
    private int type;
    private String resource;
    private String image;
    private String identity;
    private int userId;
    private int bookSearch = 0;
    private int bookClick = 0;
    private boolean bookState;
    private int bookPrice;
    private float bookDiscount;
    private String typeName;
    private UserPojo user;
}
