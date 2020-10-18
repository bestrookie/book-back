package com.bestrookie.pojo;

import com.bestrookie.service.givelike.GiveLikeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private int likeNum;
    private boolean isLike;
}
