package com.bestrookie.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author : bestrookie
 * @date : 20:36 2020/10/6
 */
@Configuration
public class ImageUploadConfig implements WebMvcConfigurer {
    @Value("${file.image-path}")
    private String path;
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/resources/static/image")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("file:" + path);
    }
}
