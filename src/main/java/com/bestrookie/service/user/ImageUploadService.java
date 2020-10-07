package com.bestrookie.service.user;

import com.bestrookie.model.MyResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : bestrookie
 * @date : 17:00 2020/10/6
 */
public interface ImageUploadService {
    MyResult imageUpload(MultipartFile file) throws IOException;
}
