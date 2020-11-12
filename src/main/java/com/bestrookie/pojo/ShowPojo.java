package com.bestrookie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 10:16 2020/11/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowPojo {
    private int showId;
    private String showImage;
    private String showRequest;
    private boolean showState;

}
