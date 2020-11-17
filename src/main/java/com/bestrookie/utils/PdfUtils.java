package com.bestrookie.utils;

import com.bestrookie.pojo.BookPojo;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
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
    public static String readPdf(String fileName){
        String str = "\\s*|\t|\r|\n";
        StringBuilder pageContent = new StringBuilder();
        try {
            PdfReader reader = new PdfReader(fileName);
            int pageNum = reader.getNumberOfPages();
            for(int i=1;i<=pageNum;i++){
                pageContent.append(PdfTextExtractor.getTextFromPage(reader, i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String text = pageContent.toString();
        Pattern pattern = Pattern.compile(str);
        Matcher m = pattern.matcher(text);
        text = m.replaceAll("");
        return text;
    }

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(new Date());
        partitionPdfFile("D:\\resources\\books\\6da76f4207b99c445bddec02aaf46b3e.pdf","D:\\resources\\bookpart\\6da76f4207b99c445bddec02aaf46b3e.pdf",1,10);
        System.out.println(new Date());
    }
    public static void partitionPdfFile(String pdfFile,String newFile, int from, int end) {
        Document document ;
        PdfCopy copy;
        try {
            PdfReader reader = new PdfReader(pdfFile);
            int n = reader.getNumberOfPages();
            if (end == 0) {
                end = n;
            }
            document = new Document(reader.getPageSize(1));
            copy = new PdfCopy(document, new FileOutputStream(newFile));
            document.open();
            for (int j = from; j <= end; j++) {
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(reader, j);
                copy.addPage(page);
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void splitPdf(BookPojo book,String filePath,String partFilePath) {
        int total = (int) Math.ceil((double) (book.getBookPage()) / (double) 100);
        int from = 1;
        int end = 100;
        String pdfFile = filePath + book.getResource();
        String newFile = partFilePath + book.getIdentity();
        log.info("开始切割");
        PdfUtils.partitionPdfFile(pdfFile, (newFile + "-"+ 0+".pdf"), 1, 10);
        for (int part = 1; part <= total; part++) {
            if (part == (total)) {
                end = book.getBookPage();
            }
            PdfUtils.partitionPdfFile(pdfFile, (newFile + "-"+ part+".pdf"), from, end);
            end += 100;
            from += 100;
        }
        log.info("切割完成");
    }
}
