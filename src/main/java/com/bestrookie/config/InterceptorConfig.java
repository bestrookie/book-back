package com.bestrookie.config;

import com.bestrookie.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : bestrookie
 * @date : 9:48 2020/10/8
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/user/login/**")
                .excludePathPatterns("/api/user/plogin/**")
                .excludePathPatterns("/api/user/getsms/**")
                .excludePathPatterns("/api/user/logout/**")
                .excludePathPatterns("/doc.html/**")
                .excludePathPatterns("/swagger-ui.html/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/api/discussions/querydiscussion/**")
                .excludePathPatterns("/api/admin/login/**")
                .excludePathPatterns("/api/show/showinfo")
                .excludePathPatterns("/api/book/fuzzy")
                .excludePathPatterns("/api/book/typetop/**")
                .excludePathPatterns("/api/book/top/**")
                .excludePathPatterns("/api/top/query/**")
                .excludePathPatterns("/api/download/download/**")
        ;

    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor(){
        return new AuthenticationInterceptor();
    }
}
