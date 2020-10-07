package com.bestrookie.constant;

import com.bestrookie.model.MyResult;
import org.springframework.http.HttpStatus;

/**
 * @author : bestrookie
 * @date : 17:31 2020/10/6
 */
public interface ResultConstant {
    MyResult EMPTY_OBJ = MyResult.failed("对象为空",null, HttpStatus.NOT_ACCEPTABLE);
    MyResult ERROR = MyResult.failed("");
}
