package com.bestrookie.service.user;
import com.bestrookie.model.MyResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author : bestrookie
 * @date : 17:12 2020/10/6
 */
@Service
public class ImageUploadServiceImpl implements ImageUploadService {
    @Value("${file.image-path}")
    private String imagePath;

    /**
     * 上传头像
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public MyResult imageUpload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()){
            return MyResult.failed("对象为空",null, HttpStatus.NOT_ACCEPTABLE);
        }
//        String root = System.getProperty("user.dir") + File.separator;
//        String filePath = "src/main/resources/static/image";
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String[] buffer = fileName.split("\\.");
        String suffix = buffer[buffer.length - 1];
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < buffer.length - 1; i++) {
            builder.append(buffer[i]);
        }
        builder.append("-" + RandomStringUtils.randomAlphanumeric(6) + ".");
        builder.append(suffix);

        File targetFile = new File(imagePath, builder.toString());
        file.transferTo(targetFile);
        return MyResult.success("image/"+ builder.toString(),"上传成功");

    }
}
