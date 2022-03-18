package org.situjunjie.chatclient;

import org.situjunjie.chatclient.client.SimpleClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ChatClientApplication {

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(ChatClientApplication.class, args);

        runSimpleChatServer(applicationContext);
    }

    /**
     * 开启简单的IM服务端
     * @param applicationContext
     */
    private static void runSimpleChatServer(ApplicationContext applicationContext) {
        SimpleClient client = applicationContext.getBean(SimpleClient.class);
        client.run();
    }

}
