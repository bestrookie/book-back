package com.bestrookie.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : bestrookie
 * @date : 14:16 2020/12/30
 */
public class UserBookUtils {
    /**
     * 将用户和收藏书籍相关联
     * @param userId 用户id
     * @param bookId 书籍id
     * @return 字符串
     */
    public static String[] userBook(int userId,int[] bookId){
        String[] str = new String[bookId.length+1];
        str[0] = String.valueOf(userId);
        for (int i = 0; i < bookId.length; i++) {
            str[i+1] = String.valueOf(bookId[i]);
        }
        return str;
    }

    /**
     *将lis转化为数组
     * @param list 集合
     * @return 数组
     */
    public static int[] conversion(List<Integer> list){
        int[] num = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            num[i] = list.get(i);
        }
        return num;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int[] conversion = conversion(list);
        for (int i : conversion) {
            System.out.println(i);
        }
    }
}
