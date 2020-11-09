package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.model.param.UploadParam;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

/**
 * @author : bestrookie
 * @date : 9:51 2020/11/7
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {
    @Value("${file.book-path}")
    private String filePath;
    /**
     * 接受切片文件
     * @param request 请求参数
     * @param response 响应参数
     * @param param 文件信息
     * @return 自定义返回类型
     */
    @PostMapping("/part")
    public MyResult bigFile(HttpServletRequest request, HttpServletResponse response, UploadParam param){
        MyResult result = null;
        try{
            String filename = param.getFileName();
            assert filename != null;
            String projectUrl = System.getProperty("user.dir").replaceAll("\\\\", "/");
            if (ServletFileUpload.isMultipartContent(request)){
                String tempFileDir = projectUrl + "/upload/"+ param.getFileName();
                File parentFileDir = new File(tempFileDir);
                if (!parentFileDir.exists()){
                    parentFileDir.mkdirs();
                }
                File tempPartFile = new File(parentFileDir, Objects.requireNonNull(param.getFile().getOriginalFilename()));
                FileUtils.copyInputStreamToFile(param.getFile().getInputStream(),tempPartFile);
                result = MyResult.success("上传成功");
            }
        }catch (Exception e){
            result =  MyResult.failed("上传失败",false,579);
        }
        response.setStatus(result.getCode());
        return  result;
    }
    /**
     * 合文件
     * @param response 响应参数
     * @param request 请求参数
     * @return 自定义返回类型
     */
    @SneakyThrows
    @GetMapping("merge")
    public MyResult mergeFile(HttpServletResponse response,HttpServletRequest request){
        MyResult result;
        String projectUrl = System.getProperty("user.dir").replaceAll("\\\\", "/");
        String fileName = request.getParameter("fileName")+".pdf";
        String path = projectUrl + "/upload/";
        File parentFileDir = new File(path + request.getParameter("fileName"));
        if (parentFileDir.isDirectory()){
            File destTempFile = new File(filePath,fileName);
            if (!destTempFile.exists()){
                destTempFile.getParentFile().mkdirs();
                try {
                    destTempFile.createNewFile();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < Objects.requireNonNull(parentFileDir.listFiles()).length; i++){
                File partFile = new File(parentFileDir,i+"-"+fileName);
                FileOutputStream destTemp = new FileOutputStream(destTempFile,true);
                FileUtils.copyFile(partFile, destTemp);
                destTemp.close();
            }
            FileUtils.deleteDirectory(parentFileDir);
            result = MyResult.success(fileName,"合并成功");
        }else {
            result = MyResult.failed("合并失败",false,579);
        }
        response.setStatus(result.getCode());
        return result;


    }
}
