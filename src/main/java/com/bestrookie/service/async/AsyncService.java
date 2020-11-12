package com.bestrookie.service.async;

import com.bestrookie.handler.MessageEventHandler;
import com.bestrookie.pojo.BookPojo;
import com.bestrookie.pojo.UserPojo;
import com.bestrookie.service.books.BookService;
import com.bestrookie.service.user.UserService;
import com.bestrookie.utils.PdfUtils;
import com.bestrookie.utils.SensitiveWordUtils;
import com.itextpdf.text.pdf.PdfReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.Set;

/**
 * @author : bestrookie
 * @date : 9:57 2020/11/8
 */
@Slf4j
@Service
public class AsyncService {
    @Value("${file.book-path}")
    private String filePath;
    @Value("${file.banWord-path}")
    private String wordPath;
    private static final int REWARD = 10;
    private static final double PRICE = 1.1;
    private static final double LIMIT = 0.1;
    @Autowired
    UserService userService;
    @Autowired
    MessageEventHandler messageEventHandler;
    @Autowired
    BookService bookService;

    /**
     * 书籍内容的合法性检验 以及价格的确定
     * @param bookPojo 书籍信息
     */
    @SneakyThrows
    @Async
    public void isBookLegal(BookPojo bookPojo){
        log.info("正在执行异步任务");
        String content = PdfUtils.readPdf(filePath + bookPojo.getResource());
        SensitiveWordUtils.init(wordPath);
        Set<String> set = SensitiveWordUtils.getSensitiveWord(content);
        double result = (double) set.size() / (double)content.length();
        System.out.println(result);
        if (result < LIMIT && content.length() > 0){
            PdfReader reader = new PdfReader(filePath + bookPojo.getResource());
            int pagesCount = reader.getNumberOfPages();
            UserPojo userPojo = userService.queryUserById(bookPojo.getUserId());
            int coin = pagesCount * REWARD + userPojo.getUserCoin();
            userService.updateUserCoin(coin,userPojo.getUserPhone());
            bookService.updateBookState(bookPojo.getIdentity(),true);
            bookService.updateBookPriceByIdentity(bookPojo.getIdentity(), (int) (pagesCount * REWARD * PRICE));
            messageEventHandler.auditPass(bookPojo.getUserId(),"你上传的书已经审核通过获得源币："+pagesCount*REWARD);
        }else {
            messageEventHandler.auditPass(bookPojo.getUserId(),"你上传的书未通过审核");
        }
        log.info("异步任务完成");

    }
}
