package com.bestrookie.controller;

import com.bestrookie.model.MyResult;
import com.bestrookie.service.user.ImageUploadService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author : bestrookie
 * @date : 9:19 2020/10/17
 */
@RestController
@RequestMapping("api/upload")
public class UploadImageController {
    @Autowired
    private ImageUploadService imageUploadService;
    @SneakyThrows
    @PostMapping("/uploadimage")
    public MyResult uploadImage(@RequestParam(value = "image") MultipartFile file, HttpServletResponse response){
        MyResult result = imageUploadService.imageUpload(file);
        response.setStatus(result.getCode());
        return  result;
    }
}
