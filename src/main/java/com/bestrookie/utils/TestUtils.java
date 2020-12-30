package com.bestrookie.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
/**
 * 基于用户的协同过滤推荐算法实现
 A a b d
 B a c
 C b e
 D c d e
 * @author Administrator
 *
 */
public class TestUtils {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入用户总量:");
        //输入用户总量
        int n = scanner.nextInt();
        int[][] sparseMatrix = new int[n][n];
        //建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        Map<String, Integer> userItemLength = new HashMap<>();
        //存储每一个用户对应的不同物品总数 eg: A 3
        Map<String, Set<String>> itemUserCollection = new HashMap<>();
        //建立物品到用户的倒排表 eg: a A B
        Set<String> items = new HashSet<>();
        //辅助存储物品集合
        Map<String, Integer> userId = new HashMap<>();
        //辅助存储每一个用户的用户ID映射
        Map<Integer, String> idUser = new HashMap<>();
        //辅助存储每一个ID对应的用户映射
        System.out.println("Input user--items maping infermation:<eg:A a b d>");
        scanner.nextLine();
        for (int i = 0; i < n ; i++){
            //依次处理N个用户 输入数据 以空格间隔
            String[] userItem = scanner.nextLine().split(" ");
            for (String s : userItem) {
                System.out.println(s);
            }
            int length = userItem.length;
            userItemLength.put(userItem[0], length-1);
            //eg: A 3
            userId.put(userItem[0], i);
            //用户ID与稀疏矩阵建立对应关系
            idUser.put(i, userItem[0]);
            //建立物品--用户倒排表
            for (int j = 1; j < length; j ++){
                if(items.contains(userItem[j])){
                    //如果已经包含对应的物品--用户映射，直接添加对应的用户
                    itemUserCollection.get(userItem[j]).add(userItem[0]);
                } else{
                    //否则创建对应物品--用户集合映射
                    items.add(userItem[j]);
                    itemUserCollection.put(userItem[j], new HashSet<>());
                    //创建物品--用户倒排关系
                    itemUserCollection.get(userItem[j]).add(userItem[0]);
                }
            }
        }
        System.out.println(itemUserCollection.toString());
        //计算相似度矩阵【稀疏】
        Set<Entry<String, Set<String>>> entrySet = itemUserCollection.entrySet();
        for (Entry<String, Set<String>> stringSetEntry : entrySet) {
            Set<String> commonUsers = stringSetEntry.getValue();
            for (String userU : commonUsers) {
                for (String userV : commonUsers) {
                    if (userU.equals(userV)) {
                        continue;
                    }
                    sparseMatrix[userId.get(userU)][userId.get(userV)] += 1;
                    //计算用户u与用户v都有正反馈的物品总数
                }
            }
        }
        System.out.println(userItemLength.toString());
        System.out.println("Input the user for recommendation:<eg:A>");
        String recommendUser = scanner.nextLine();
        System.out.println(userId.get(recommendUser));
        //计算用户之间的相似度【余弦相似性】
        int recommendUserId = userId.get(recommendUser);
        for (int j = 0;j < sparseMatrix.length; j++) {
            if(j != recommendUserId){
                System.out.println(idUser.get(recommendUserId)+"--"+idUser.get(j)+"相似度:"+sparseMatrix[recommendUserId][j]/Math.sqrt(userItemLength.get(idUser.get(recommendUserId))*userItemLength.get(idUser.get(j))));
            }
        }
        //计算指定用户recommendUser的物品推荐度
        for (String item: items){
            //遍历每一件物品
            Set<String> users = itemUserCollection.get(item);
            //得到购买当前物品的所有用户集合
            if(!users.contains(recommendUser)){
                //如果被推荐用户没有购买当前物品，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                for (String user: users){
                    itemRecommendDegree += sparseMatrix[userId.get(recommendUser)][userId.get(user)]/Math.sqrt(userItemLength.get(recommendUser)*userItemLength.get(user));
                    //推荐度计算
                }
                System.out.println("The item "+item+" for "+recommendUser +"'s recommended degree:"+itemRecommendDegree);
            }
        }
        scanner.close();
    }
}
