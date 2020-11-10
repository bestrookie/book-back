package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author : bestrookie
 * @date : 10:10 2020/10/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDiscussionsPojo {
    private int bdId;
    private int userId;
    private String bdName;
    private String bdPhoto;
    private String bdDes;
    private long bdDate;
    private String userName;
    private int peopleNum;
    private int bookId;
}
