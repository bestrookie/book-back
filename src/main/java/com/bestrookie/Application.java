package com.bestrookie;


import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * @Date: 2020:10:04
 * @author bestrookie
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Autowired
    private SocketIOServer socketIOServer;
    @Override
    public void run(String... args) throws Exception {
        socketIOServer.start();
        log.info("socket启动");
    }
}
