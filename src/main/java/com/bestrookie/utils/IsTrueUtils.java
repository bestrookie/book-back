package com.bestrookie.utils;

import com.bestrookie.pojo.BookPojo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;

import java.util.regex.Pattern;

/**
 * @author : bestrookie
 * @date : 20:39 2020/10/20
 */
public class IsTrueUtils {
    public static boolean isTrue(String str){
        if (str ==null || str.isEmpty()){
            return false;
        }
        boolean flg = false;
        final String rules = "^[-\\+]?[\\d]{1,9}$";
        Pattern pattern = Pattern.compile(rules);
        boolean matches = pattern.matcher(str).matches();
        if (matches){
            int number = Integer.parseInt(str);
            if (number > 0 && number < 214748364){
                flg = true;
            }
        }
        return flg;
    }
}
