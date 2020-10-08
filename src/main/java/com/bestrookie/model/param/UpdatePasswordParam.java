package com.bestrookie.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 16:51 2020/10/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordParam {
    private String password;
    private String code;
}
