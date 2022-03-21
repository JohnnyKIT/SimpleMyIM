package org.situjunjie.chatclient.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @className: CommandController
 * @description: 命令接收和分发
 * @author: situjunjie
 * @date: 2022/3/20
 **/
@Slf4j
@Service
public class CommandController {

    private LoginCommand loginCommand = new LoginCommand();

    private ChatCommand chatCommand = new ChatCommand();

    private Map<Integer,BaseCommand> commandMap;


    public static final String THREAD_NAME = "命令线程";

    private Scanner sc = new Scanner(System.in);


    public CommandController(){
        initCommandMap();
    }

    /**
     * 初始化cmmandMap
     */
    private void initCommandMap(){
        commandMap = new HashMap<>();
        commandMap.put(loginCommand.getKey(),loginCommand);
        commandMap.put(chatCommand.getKey(), chatCommand);
    }

    public void run(){
        Thread.currentThread().setName(THREAD_NAME);
        StringBuilder sb = new StringBuilder("命令菜单：");
        for (Integer key : commandMap.keySet()) {
            sb.append(key).append("->").append(commandMap.get(key).getDescription()).append("  ");
        }
        String menue = sb.toString();
        while(true){
            log.info(menue);
            String input = sc.nextLine();
            if(!commandMap.containsKey(Integer.valueOf(input))){
                //输入不在菜单选项中
                log.error("请重新输入输入正确选项");
                continue;
            }
            BaseCommand baseCommand = commandMap.get(Integer.valueOf(input));
            baseCommand.exec(sc);
        }
    }
}
