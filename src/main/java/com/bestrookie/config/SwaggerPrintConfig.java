package com.bestrookie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * @author : bestrookie
 * @date : 10:20 2020/9/26
 */
@Component
@Slf4j
public class SwaggerPrintConfig implements ApplicationListener<WebServerInitializedEvent> {
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int port = event.getWebServer().getPort();
            String applicationName = event.getApplicationContext().getApplicationName();
            log.info("项目启动成功！接口文档地址: http://"+hostAddress+":"+event.getWebServer().getPort()+applicationName+"/doc.html");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
