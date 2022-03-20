package org.situjunjie.chatserver.processor;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;
import org.situjunjie.chatcommon.client.ClientSession;
import org.situjunjie.chatserver.sender.MessageSender;
import org.situjunjie.chatserver.server.ClientSessionMap;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @className: LoginProcessor
 * @description: 处理登录请求
 * @author: situjunjie
 * @date: 2022/3/20
 **/
@Service
@Slf4j
public class LoginProcessor {

    public void login(ProtoMsg.Message message, Channel channel) {
        log.info("收到用户登录请求 username={}",message.getLoginRequest().getUsername());
        ProtoMsg.LoginRequest loginRequest = message.getLoginRequest();
        if(validateUser(loginRequest.getUsername(),loginRequest.getPassword())){
            ClientSession session = new ClientSession();
            session.setSessionId(UUID.randomUUID().toString());
            session.setChannel(channel);
            session.setUserId(message.getLoginRequest().getUsername());
            ClientSessionMap.putSession(session.getSessionId(), session);
            responseClient(channel,session);
            log.info("{} 用户登录成功,当前在线人数:{}",session.getUserId(), ClientSessionMap.count());
        }
    }

    private void responseClient(Channel channel, ClientSession session) {
        ProtoMsg.LoginReponse longinResponse = ProtoMsg.LoginReponse.newBuilder().setResult(true).setInfo("登录成功").build();
        ProtoMsg.Message message = ProtoMsg.Message.newBuilder().setLoginResponse(longinResponse)
                .setTypeValue(ProtoMsg.MsgType.LOGIN_RESPONSE.getNumber())
                .setSessionid(session.getSessionId())
                .setType(ProtoMsg.MsgType.LOGIN_RESPONSE).build();
        MessageSender.sendMessage(channel,message);
    }

    private boolean validateUser(String username, String password) {
        //先不增加登录校验
        return true;
    }
}
