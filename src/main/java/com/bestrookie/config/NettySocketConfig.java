package com.bestrookie.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : bestrookie
 * @date : 15:07 2020/10/25
 */
@Configuration
public class NettySocketConfig {
    @Bean
    public SocketIOServer socketIOServer(){
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setHostname("192.168.1.162");
//       configuration.setHostname("192.168.43.168");
        configuration.setPort(8081);
        configuration.setTransports( Transport.WEBSOCKET,Transport.POLLING);
        configuration.setOrigin(":*:");
        return new SocketIOServer(configuration);
    }
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(){
        return new SpringAnnotationScanner(socketIOServer());
    }
}
