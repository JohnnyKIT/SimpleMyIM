package org.situjunjie.chatclient.command;

import lombok.extern.slf4j.Slf4j;
import org.situjunjie.chatclient.client.SimpleClient;
import org.situjunjie.chatclient.processor.ChatProcssor;
import org.springframework.util.StringUtils;

import java.util.Scanner;

/**
 * @className: PrivateChatCommand
 * @description: 私聊命令
 * @author: situjunjie
 * @date: 2022/3/29
 **/
@Slf4j
public class PrivateChatCommand implements BaseCommand{

    public static final int KEY = 4;

    private ChatProcssor chatProcssor = new ChatProcssor();

    @Override
    public Integer getKey() {
        return KEY;
    }

    @Override
    public String getDescription() {
        return "私聊";
    }

    @Override
    public void exec(Scanner scanner) {
        log.info("请输入私聊用户Id");
        String userId = scanner.nextLine();
        String sessionId = SimpleClient.session.getSessionId();
        if(StringUtils.isEmpty(sessionId)) {
            log.info("请先登录");
        }
        while (true){
            log.info("请输入与{}的私密消息(输入q退出):",userId);
            String message = scanner.nextLine();
            if("q".equals(message)){
                break;
            }
            chatProcssor.sendPrivateMessage(message,userId);
        }
    }
}
