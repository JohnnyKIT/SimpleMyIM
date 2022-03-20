package org.situjunjie.chatclient.sender;

import org.situjunjie.chatcommon.client.ClientSession;
import org.situjunjie.chatclient.client.SimpleClient;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;
import org.springframework.stereotype.Service;


/**
 * @className: LoginSender
 * @description: 发送登录请求
 * @author: situjunjie
 * @date: 2022/3/20
 **/
@Service
public class MessageSender{


    /**
     * 发送客户端等登录请求
     * @param message
     */
    public void send(ProtoMsg.Message message) {
        ClientSession session = SimpleClient.session;
        session.getChannel().writeAndFlush(message);
    }
}
