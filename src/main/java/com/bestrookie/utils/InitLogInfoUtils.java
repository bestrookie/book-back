package com.bestrookie.utils;

import com.bestrookie.pojo.LogPojo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : bestrookie
 * @date : 9:58 2020/11/3
 */
public class InitLogInfoUtils {
    public static LogPojo initLogInfo(HttpServletRequest request,String des){
        LogPojo logPojo = new LogPojo();
        logPojo.setLogDate(System.currentTimeMillis());
        logPojo.setUserId(TokenUtils.getId(request.getHeader("authorization")));
        logPojo.setLogIp(IpUtils.getIpAddress(request));
        logPojo.setLogOperate(des);
        return logPojo;
    }
}
