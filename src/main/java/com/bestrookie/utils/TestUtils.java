package com.bestrookie.utils;

import com.bestrookie.pojo.BookPojo;
import com.bestrookie.service.books.BookService;
import com.bestrookie.service.books.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : bestrookie
 * @date : 10:36 2020/11/17
 */
@Slf4j
public class TestUtils {
    private static String filePath = "D:\\resources\\books\\";
    private static String partFilePath = "D:\\resources\\bookpart\\";
    public void splitPdf(int bookId){
        BookService bookService = new BookServiceImpl();
        BookPojo book = bookService.queryBookById(bookId);
        int total = (int)Math.ceil((double)(book.getBookPage()) / (double)100);
        int from = 0;
        int end = 10;
        String pdfFile = filePath + book.getResource();
        String newFile = partFilePath + book.getResource();
        log.info("开始切割");
        for (int part = 0; part < total + 1; part++) {
            if (part == (total)){
                end = (book.getBookPage() - part*100);
            }
            PdfUtils.partitionPdfFile(pdfFile,newFile+part,from,end);
            end += 100;
            from += 100;
        }
        log.info("切割完成");
    }

    public static void main(String[] args) {
        TestUtils test = new TestUtils();
        test.splitPdf(9);
    }
}
