package org.situjunjie.chatclient.processor;

import org.situjunjie.chatclient.client.SimpleClient;
import org.situjunjie.chatclient.sender.MessageSender;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: LoginProcessor
 * @description: 登录请求处理
 * @author: situjunjie
 * @date: 2022/3/20
 **/
@Service
public class LoginProcessor {

    @Autowired
    MessageSender messageSender = new MessageSender();

    /**
     * 处理客户端的登录请求
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        ProtoMsg.LoginRequest loginRequest= ProtoMsg.LoginRequest.newBuilder()
                .setUsername(username).setPassword(password)
                .build();
        ProtoMsg.Message message = ProtoMsg.Message.newBuilder().setLoginRequest(loginRequest)
                .setType(ProtoMsg.MsgType.LOGIN_REQUEST)
                .setTypeValue(ProtoMsg.MsgType.LOGIN_REQUEST.getNumber())
                .build();
        SimpleClient.session.setUserId(username);
        messageSender.send(message);
    }
}
