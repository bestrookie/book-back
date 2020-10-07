package com.bestrookie.utils;

import java.io.File;

/**
 * @author : bestrookie
 * @date : 9:58 2020/10/6
 */
public class UploadUtils {
    public final static String IMAGE_PATH = "static/image";
    public static File getImageFile(){
        String imagePath = new String("src/main/resources/"+IMAGE_PATH);
        File image = new File(imagePath);
        if (!image.exists()){
            image.mkdirs();
        }
        return image;
    }
}
