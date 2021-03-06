package com.bestrookie.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * @author : bestrookie
 * @date : 15:01 2020/9/24
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig{
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bestrookie.controller"))
                .build();
    }
    //配置Swagger信息
    private ApiInfo apiInfo(){
        Contact contact = new Contact("bestrookie","https://bestrookie.cn","******");
        return new ApiInfo(
                "SwaggerAPI文档",
                "毕业设计",
                "v1.0",
                "https://bestrookie.cn",
                contact,
                "Apache2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }

}
