package com.bestrookie.utils;

import java.util.*;

/**
 * @author : bestrookie
 * @date : 16:33 2020/12/28
 */
public class MapSortByValueUtils {
    public static void main(String[] args) {
        Map<Integer, Double> map = new HashMap<Integer, Double>();
        map.put(1, 0.35);
        map.put(3, 0.33);
        map.put(12, 0.11);
        map.put(22, 0.55);
        map.put(4, 1.35);

        //将map.entrySet()转换成list
        List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        for (Map.Entry<Integer, Double> mapping : list) {
            System.out.println(mapping.getKey() + ":" + mapping.getValue());
        }
    }

    /**
     * 给map集合排序
     * @param map map集合
     * @return map集合
     */
    public static HashMap<Integer, Double> mapSort(HashMap<Integer, Double> map){
        List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return map;
    }
    public static int[] getMapKey(HashMap<Integer, Double> map){
        int [] num = new int[map.size()];
        int i = 0;
        for (Integer s : map.keySet()){
            num[i] = s;
            i++;
        }
        return num;
    }
}
