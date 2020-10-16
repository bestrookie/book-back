package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

/**
 * @author : bestrookie
 * @date : 19:42 2020/10/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicPojo {
    private int dId;
    private int userId;
    private long dDate;
    private  String dContent;
    private String dAbstract;
    private int bdId;
    private UserPojo user;
}
