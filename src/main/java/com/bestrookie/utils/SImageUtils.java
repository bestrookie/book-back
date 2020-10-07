package com.bestrookie.utils;

import java.util.HashMap;

/**
 * @author : bestrookie
 * @date : 16:36 2020/10/5
 */
public class SImageUtils {
    public static HashMap<String, String> sImage(String token,String image){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token",token);
        hashMap.put("image",image);
        return hashMap;
    }
}
