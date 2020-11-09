package com.bestrookie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : bestrookie
 * @date : 15:45 2020/11/7
 */
public class BookUploadConfig implements WebMvcConfigurer {
    @Value("${file.book-path}")
    private String path;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/resources/static/books")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("file:" + path);
    }
}
