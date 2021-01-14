package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.pojo.BookLimitPojo;
import com.bestrookie.pojo.BookPojo;
import com.bestrookie.service.booklimit.BookLimitService;
import com.bestrookie.service.books.BookService;
import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @author : bestrookie
 * @date : 14:44 2020/11/17
 */
@RestController
@RequestMapping("api/read")
public class ReadController {
    @Value("${file.bookPart-path}")
    private String partFilePath;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookLimitService limitService;
    @GetMapping("/tryread")
    public MyResult tryReadBook(HttpServletResponse response, @RequestParam int bookId){
        if (bookService.isBookTrue(bookId)){
            BookPojo book = bookService.queryBookById(bookId);
            String filePath = partFilePath + book.getIdentity()+ "-" + 0 + ".pdf";
            File file = new File(filePath);
            if (file.exists()) {
                byte[] buffer = new byte[1024];
                //输出流
                OutputStream os;
                try (FileInputStream fis = new FileInputStream(file);
                     BufferedInputStream bis = new BufferedInputStream(fis)) {
                    os = response.getOutputStream();
                    response.setHeader("Content-Type","application/pdf");
                    response.setHeader("Cache-Control", "must-revalidation/proxy-revalidation");
                    response.setDateHeader("Expires",4102416000000L);
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return MyResult.success(true);
            }else {
                response.setStatus(404);
                return MyResult.failed("未找到资源",false,530);
            }
        }else {
            response.setStatus(530);
            return MyResult.failed("书籍已下架",false,530);
        }
    }
    @GetMapping("/read")
    public void readBook(HttpServletRequest request, HttpServletResponse response, @RequestParam int bookId,@RequestParam int part){
        if (bookService.isBookTrue(bookId)){
            BookLimitPojo limit = limitService.queryById(TokenUtils.getId(request.getHeader("authorization")), bookId);
            if (part <= limit.getLimitPage() || limit.isLimitAll()){
                BookPojo book = bookService.queryBookById(bookId);
                String filePath = partFilePath + book.getIdentity()+ "-" + part + ".pdf";
                File file = new File(filePath);
                if (file.exists()) {
                    byte[] buffer = new byte[1024];
                    //输出流
                    OutputStream os;
                    try (FileInputStream fis = new FileInputStream(file);
                         BufferedInputStream bis = new BufferedInputStream(fis)) {
                        os = response.getOutputStream();
                        response.setHeader("Content-Type","application/pdf");
                        response.setHeader("Cache-Control", "must-revalidation/proxy-revalidation");
                        response.setDateHeader("Expires",86400000L);
                        int i = bis.read(buffer);
                        while (i != -1) {
                            os.write(buffer);
                            i = bis.read(buffer);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    response.setStatus(404);
                }
            }else {
                response.setStatus(528);
            }
        }else {
            response.setStatus(530);
        }
    }

}
