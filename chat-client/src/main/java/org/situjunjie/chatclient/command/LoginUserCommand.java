package org.situjunjie.chatclient.command;

import org.situjunjie.chatclient.processor.LoginUserProcessor;

import java.util.Scanner;

/**
 * @className: LoginUserCommand
 * @description: 在线用户列表命令
 * @author: situjunjie
 * @date: 2022/3/21
 **/
public class LoginUserCommand implements BaseCommand{

    public static final int KEY = 3;

    private LoginUserProcessor loginUserProcessor = new LoginUserProcessor();

    @Override
    public Integer getKey() {
        return KEY;
    }

    @Override
    public String getDescription() {
        return "在线用户列表";
    }

    @Override
    public void exec(Scanner scanner) {
        loginUserProcessor.listOnlineUser();
    }
}
