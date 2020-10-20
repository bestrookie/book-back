package com.bestrookie.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 9:21 2020/10/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseDynamicReviewParam {
    private String reviewContent;
    private int parentId = 0;
    private int supParentId =0;
    private int dynamicId;
}
