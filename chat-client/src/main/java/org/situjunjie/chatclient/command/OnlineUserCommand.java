package org.situjunjie.chatclient.command;

import java.util.Scanner;

/**
 * @className: OnlineUserCommand
 * @description: 在线人数命令行
 * @author: situjunjie
 * @date: 2022/3/21
 **/
public class OnlineUserCommand implements BaseCommand{

    public static final int KEY = 2;

    @Override
    public Integer getKey() {
        return KEY;
    }

    @Override
    public String getDescription() {
        return "在线用户";
    }

    @Override
    public void exec(Scanner scanner) {
        //1.发送服务端获取在线用户列表

    }
}
