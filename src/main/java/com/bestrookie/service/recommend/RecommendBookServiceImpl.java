package com.bestrookie.service.recommend;

import com.bestrookie.mapper.BookMapper;
import com.bestrookie.mapper.CollectionMapper;
import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.BookPojo;
import com.bestrookie.utils.MapSortByValueUtils;
import com.bestrookie.utils.UserBookUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author : bestrookie
 * @date : 15:54 2020/12/28
 */
@Service
@Transactional
public class RecommendBookServiceImpl implements RecommendBookService{
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private CollectionMapper collectionMapper;
    final static int BOOK_NUM = 6;
    @Override
    public MyResult recommendBook(int bookId,int nowUser) {
        List<BookPojo> allBook = bookMapper.bookTop();
        List<BookPojo> recommendedBook = new ArrayList<>();
        List<Integer> collectBooks = collectionMapper.getCollectBookId(nowUser);
        //如果还没有收藏启用模拟推荐
        if (collectBooks.size() == 0){
            int i = 0 ;
            for (BookPojo bookPojo : allBook) {
                if (bookPojo.getBookId() == bookId){
                    allBook.remove(i);
                    break;
                }
                i++;
            }
            for (int k = 0; k < BOOK_NUM; k++) {
                recommendedBook.add(allBook.get(k));
            }
        }else {
            //真正的推荐算法
            List<Integer> user = collectionMapper.getUser();
            HashSet<Integer> set = new HashSet<>(user);
            user.clear();
            user.addAll(set);
            HashMap<Integer, Double> map = MapSortByValueUtils.mapSort(userRecommend(UserBookUtils.conversion(user), nowUser));
            int[] books = MapSortByValueUtils.getMapKey(map);
            //便利map如何书的id不等于当前书籍的id 添加到推荐书籍集合
            for (int book : books) {
                if (book != bookId){
                    recommendedBook.add(bookMapper.queryBookById(book));
                }
                if (recommendedBook.size() > 5){
                    break;
                }
            }
            //当推荐书籍不满足六本,先将排行耪删除掉已经推荐的书籍
            for (int book : books) {
                for (int j = 0; j < allBook.size(); j++) {
                    if (book == allBook.get(j).getBookId()) {
                        allBook.remove(j);
                        break;
                    }
                }
            }
            //补充完整六本推荐
            if (recommendedBook.size() < BOOK_NUM){
                int k = 0;
                do {
                    recommendedBook.add(allBook.get(k));
                    k++;
                } while (recommendedBook.size() <= 5);
            }
        }
        return MyResult.success(recommendedBook,"推荐书籍");
    }


    /**
     * 书籍推荐算法计算相似度
     * @param userIds 用户id列表
     * @param nowUser 当前用户id
     * @return map<书籍id/相似读>
     */
    public HashMap<Integer, Double> userRecommend(int [] userIds,int nowUser){
        HashMap<Integer, Double> result = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        //用户总量
        int n = userIds.length;
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
        for (int i = 0; i < n ; i++){
            //用户的喜欢书籍id
            List<Integer> bookIds = collectionMapper.getCollectBookId(userIds[i]);
            int[] collectBookId = UserBookUtils.conversion(bookIds);
            //依次处理N个用户 输入数据 以空格间隔
            String[] userItem = UserBookUtils.userBook(userIds[i],collectBookId);
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
        Set<Map.Entry<String, Set<String>>> entrySet = itemUserCollection.entrySet();
        for (Map.Entry<String, Set<String>> stringSetEntry : entrySet) {
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
        String recommendUser = String.valueOf(nowUser);
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
                result.put(Integer.valueOf(item),itemRecommendDegree);
            }

        }
        scanner.close();
        return result;
    }
}
