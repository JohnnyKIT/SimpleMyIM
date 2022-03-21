package org.situjunjie.chatclient.command;

import lombok.extern.slf4j.Slf4j;
import org.situjunjie.chatclient.client.SimpleClient;
import org.situjunjie.chatclient.processor.ChatProcssor;
import org.situjunjie.chatclient.sender.MessageSender;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.Scanner;

/**
 * @className: ChatCommand
 * @description: 群聊命令行
 * @author: situjunjie
 * @date: 2022/3/21
 **/
@Slf4j
public class ChatCommand implements BaseCommand{

    public static final int KEY = 2;

    private ChatProcssor chatProcssor = new ChatProcssor();

    @Override
    public Integer getKey() {
        return KEY;
    }

    @Override
    public String getDescription() {
        return "群聊";
    }

    @Override
    public void exec(Scanner scanner) {
        while(true){
            System.out.println("请输入消息,输入q退出");
            String message = scanner.nextLine();
            if("q".equals(message)){
                break;
            }
            //判断登录状态
            String sessionId = SimpleClient.session.getSessionId();
            if(sessionId==null){
                log.error("请先登录");
                break;
            }
            chatProcssor.sendCommonChatMssage(message);
        }

    }
}
