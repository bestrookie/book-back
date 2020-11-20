package com.bestrookie.service.ItemCF;
import com.bestrookie.service.data.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author : bestrookie
 * @date : 15:02 2020/11/19
 */
@Service
public class ItemCFServiceImpl {
    @Autowired
    private DataService dataService;
    private void getUserLikeType(int userId){
        int [] data = (int[]) dataService.userBookData(userId).getObj();
    }
}
