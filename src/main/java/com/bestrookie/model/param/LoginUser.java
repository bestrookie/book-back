package com.bestrookie.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : bestrookie
 * @date : 16:53 2020/10/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private String phone;
    private String password;
}
