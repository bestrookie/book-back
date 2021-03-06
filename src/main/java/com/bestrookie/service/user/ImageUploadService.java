package com.bestrookie.service.user;

import com.bestrookie.model.MyResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : bestrookie
 * @date : 17:00 2020/10/6
 */
public interface ImageUploadService {
    /**
     * 设置头像
     * @param file
     * @return
     * @throws IOException
     */
    MyResult imageUpload(MultipartFile file) throws IOException;
}
