package com.bestrookie.utils;

import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.core.IKSegmenter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : bestrookie
 * @date : 10:56 2020/10/9
 * 敏感词分词工具
 */
public class SensitiveWordUtils {

    //敏感词集合
    public static HashMap sensitiveWordMap;

    //初始化敏感词库
    public static synchronized void init(){
        String filePath = "D:\\resources\\banwords\\banword.txt";
        Set<String> sensitiveWordSet = readTxtByLine(filePath);
        //初始化敏感词容器，减少扩容操作
        sensitiveWordMap=new HashMap(sensitiveWordSet.size());
        for(String sensitiveWord :sensitiveWordSet){
            sensitiveWordMap.put(sensitiveWord, sensitiveWord);
        }
    }
    //判断文字是否包含敏感字符
    public static boolean contains(String txt) throws IOException{
        boolean flag=false;
        List<String> wordList=segment(txt);
        for(String word:wordList){
            if(sensitiveWordMap.get(word)!=null){
                return true;
            }
        }
        return flag;
    }
    /**
     * 对语句进行分词
     * @param txt
     * @return
     * @throws IOException
     */
    private static List<String> segment(String txt) throws IOException {
        List<String> list=new ArrayList<>();
        StringReader re=new StringReader(txt);
        IKSegmenter ik=new IKSegmenter(re,true);
        Lexeme lex;
        while ((lex = ik.next()) != null) {
            list.add(lex.getLexemeText());
        }
        return list;
    }

    /**
     * 获取语句中的敏感词，存入set集合
     * @return 敏感词集合
     * @throws IOException
     */
    private static Set<String> getSensitiveWord(String txt) throws IOException {
        Set<String> sensitiveWordSet=new HashSet<>();
        //将语句进行分词
        List<String> wordList=segment(txt);
        for(String word:wordList){
            if(sensitiveWordMap.get(word)!=null){
                sensitiveWordSet.add(word);
            }
        }
        return sensitiveWordSet;
    }
    /**
     * 将语句中的敏感词进行替换
     * @param txt 语句
     * @param replaceChar 替换字符
     * @return 替换之后的语句
     * @throws IOException
     */
    private static String replaceSensitiveWord(String txt, char replaceChar) throws IOException {
        String resultTxt=txt;
        //获取所有敏感词
        Set<String> sensitiveWordList=getSensitiveWord(txt);

        String replaceString;
        for(String sensitiveWord :sensitiveWordList){
            replaceString=getReplaceChars(replaceChar,sensitiveWord.length());
            resultTxt=resultTxt.replaceAll(sensitiveWord, replaceString);
        }

        return resultTxt;
    }
    /**
     * 将敏感词进行替换成指定字符
     * @param replaceChar
     * @param length
     * @return
     */
    private static String getReplaceChars(char replaceChar, int length) {
        String resultReplace=String.valueOf(replaceChar);
        for(int i=1;i<length;i++){
            resultReplace+=replaceChar;
        }
        return resultReplace;
    }
    /**
     * 将敏感词替换成指定字符串
     * @param txt
     * @param replaceStr
     * @return
     * @throws IOException
     */
    private static String replaceSensitiveWord(String txt, String replaceStr) throws IOException {
        String resultTxt=txt;
        Set<String> sensitiveWordList=getSensitiveWord(txt);
        for(String sensitiveWord:sensitiveWordList){
            resultTxt=resultTxt.replaceAll(sensitiveWord, replaceStr);
        }
        return resultTxt;
    }
    public static Set<String> readTxtByLine(String path){
        Set<String> keyWordSet = new HashSet<String>();
        File file=new File(path);
        if(!file.exists()){      //文件流是否存在
            return keyWordSet;
        }
        BufferedReader reader=null;
        String temp=null;
        //int line=1;
        try{
            //reader=new BufferedReader(new FileReader(file));这样在web运行的时候，读取会乱码
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            while((temp=reader.readLine())!=null){
                //System.out.println("line"+line+":"+temp);
                keyWordSet.add(temp);
                //line++;
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(reader!=null){
                try{
                    reader.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return keyWordSet;
    }
    //测试
    public static void main(String[] args) {
        //初始化敏感词库
        SensitiveWordUtils.init();
        //需要处理的目标字符串
        String str="大sb";

        //判断是否包含敏感词
        try {
            boolean result= SensitiveWordUtils.contains(str);
            System.out.println("目标中是否含有敏感词"+result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取语句中的敏感词
        Set<String> set;
        try {
            set = SensitiveWordUtils.getSensitiveWord(str);
            System.out.println("语句中包含的敏感词的个数为："+set.size()+".包含："+set);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //替换语句中的敏感词
        String filterStr;
        try {
            filterStr = SensitiveWordUtils.replaceSensitiveWord(str,'*');
            System.out.println(filterStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String filterStr2;
        try {
            filterStr2 = SensitiveWordUtils.replaceSensitiveWord(str, "*敏感词*");
            System.out.println(filterStr2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
