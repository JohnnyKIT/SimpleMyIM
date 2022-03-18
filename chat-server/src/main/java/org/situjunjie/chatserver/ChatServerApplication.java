package org.situjunjie.chatserver;

import org.situjunjie.chatserver.server.SimpleChatServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ChatServerApplication {

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(ChatServerApplication.class, args);

        runSimpleChatServer(applicationContext);
    }

    /**
     * 开启简单的IM服务端
     * @param applicationContext
     */
    private static void runSimpleChatServer(ApplicationContext applicationContext) {
        SimpleChatServer server = applicationContext.getBean(SimpleChatServer.class);
        server.run();
    }

}
