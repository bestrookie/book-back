package com.bestrookie.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author : bestrookie
 * @date : 15:15 2020/10/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyResult {
    private long code;
    private String msg;
    private Object obj;

    /**
     * 成功返回对象
     * @param obj
     * @return
     */
    public static MyResult success(Object obj){
        return new MyResult(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),obj);
    }

    /**
     * 成功返回对象和消息
     * @param obj
     * @param msg
     * @return
     */
    public static MyResult success(Object obj,String msg){
        return new MyResult(ResultCode.SUCCESS.getCode(),msg,obj);
    }

    /**
     * 失败返回消息
     * @param msg
     * @return
     */
    public static MyResult failed(String msg){
        return new MyResult(ResultCode.FAILED.getCode(), msg,null);
    }

}
