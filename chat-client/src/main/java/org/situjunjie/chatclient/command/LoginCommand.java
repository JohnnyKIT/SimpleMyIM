package org.situjunjie.chatclient.command;

import lombok.extern.slf4j.Slf4j;
import org.situjunjie.chatclient.processor.LoginProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * @className: LoginCommand
 * @description: 接收处理登录命令
 * @author: situjunjie
 * @date: 2022/3/20
 **/
@Slf4j
public class LoginCommand implements BaseCommand{

    public static final Integer KEY = 1;

    @Autowired
    LoginProcessor loginProcessor = new LoginProcessor();

    @Override
    public Integer getKey() {
        return KEY;
    }

    @Override
    public String getDescription() {
        return "登录";
    }

    @Override
    public void exec(Scanner scanner) {
        String[] split;
        while(true){
            log.info("请输入登录用户名和密码(root@123456)");
            String input = scanner.nextLine();
            split = input.split("@");
            if(split.length!=2){
                log.error("用户名密码输入格式不正确，请重新输入");
                continue;
            }
            break;
        }
        String username = split[0];
        String password = split[1];
        loginProcessor.login(username,password);
    }
}
