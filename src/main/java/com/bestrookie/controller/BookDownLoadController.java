package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.BookPojo;
import com.bestrookie.service.booklimit.BookLimitService;
import com.bestrookie.service.books.BookService;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author : bestrookie
 * @date : 16:47 2020/11/16
 */
@RestController
@RequestMapping("/api/download")
public class BookDownLoadController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookLimitService limitService;
    @Value("${file.book-path}")
    private String filePath;

    @GetMapping("/download")
    public MyResult downLoad(HttpServletResponse response, @RequestParam int bookId,@RequestParam String token, HttpServletRequest request){
        MyResult result;
        BookPojo bookPojo = bookService.queryBookById(bookId);
        if (limitService.isAllLimit(TokenUtils.getId(token), bookId)) {
            File file = new File(filePath + bookPojo.getResource());
            if (file.exists()) {
                byte[] buffer = new byte[1024];
                //输出流
                OutputStream os;
                try (FileInputStream fis = new FileInputStream(file);
                     BufferedInputStream bis = new BufferedInputStream(fis)) {
                    os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                result = MyResult.success(true);
            }else {
                result = MyResult.failed("找不到文件", false, 528);
            }
        } else {
            result = MyResult.failed("暂未解锁,无法下载", false, 528);

        }
        response.setStatus(result.getCode());
        return result;
    }
}

