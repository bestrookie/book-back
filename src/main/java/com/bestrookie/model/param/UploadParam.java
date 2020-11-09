package com.bestrookie.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : bestrookie
 * @date : 10:36 2020/11/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadParam {
    String fileName;
    MultipartFile file;
}
