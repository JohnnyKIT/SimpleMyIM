package org.situjunjie.chatclient.command;

import java.util.Scanner;

/**
 * @className: BaseCommand
 * @description: 命令基类
 * @author: situjunjie
 * @date: 2022/3/20
 **/
public interface BaseCommand {

    Integer getKey();

    String getDescription();

    void exec(Scanner scanner);
}
