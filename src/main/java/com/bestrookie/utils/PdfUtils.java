package com.bestrookie.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.EOFException;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : bestrookie
 * @date : 21:08 2020/11/7
 */
@Slf4j
public class PdfUtils {
    /**
     * 读取pdf页数
     * @param path 路径
     * @return 页数
     */
    @SneakyThrows
    public static int pagesCount(String path){
        File file = new File(path);
        PDDocument pdDocument = PDDocument.load(file);
        int pages = pdDocument.getNumberOfPages();
        pdDocument.close();
        return pages;
    }
    @SneakyThrows
    public static String getContent(String filePath) throws EOFException{
        InputStream inputStream;
        PDFParser pdfParser = new PDFParser(new org.apache.pdfbox.io.RandomAccessFile(new File(filePath),"rw"));
        pdfParser.parse();
        PDDocument pdDocument = pdfParser.getPDDocument();
        String text = new PDFTextStripper().getText(pdDocument);
        pdDocument.close();
        return text;
    }
    @SneakyThrows
    public static String getReallyContent(String filePath){
        String str = "\\s*|\t|\r|\n";
        InputStream inputStream;
        PDFParser pdfParser = new PDFParser(new org.apache.pdfbox.io.RandomAccessFile(new File(filePath),"rw"));
        pdfParser.parse();
        PDDocument pdDocument = pdfParser.getPDDocument();
        String text = new PDFTextStripper().getText(pdDocument);
        Pattern pattern = Pattern.compile(str);
        Matcher m = pattern.matcher(text);
        text = m.replaceAll("");
        pdDocument.close();
        return text;
    }
    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(new Date());
        String str = "\\s*|\t|\r|\n";
        String content = getContent("F:\\我的坚果云\\资料\\JAVA核心知识点整理.pdf");
        Pattern pattern = Pattern.compile(str);
        Matcher m = pattern.matcher(content);
        content = m.replaceAll("");
        System.out.println(getContent("F:\\我的坚果云\\资料\\Spring实战（第3版）.pdf"));
        System.out.println(getContent("F:\\我的坚果云\\资料\\Spring实战（第3版）.pdf").length());
        System.out.println(content.length());
        System.out.println(new Date());
    }
}
