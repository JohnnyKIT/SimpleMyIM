package org.situjunjie.chatclient.processor;

import org.situjunjie.chatclient.client.SimpleClient;
import org.situjunjie.chatclient.sender.MessageSender;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;
import org.situjunjie.chatcommon.client.ClientSession;

/**
 * @className: LoginUserProcessor
 * @description: 在线用户处理器
 * @author: situjunjie
 * @date: 2022/3/21
 **/
public class LoginUserProcessor {

    private MessageSender messageSender = new MessageSender();

    public void listOnlineUser() {
        ClientSession session = SimpleClient.session;
        ProtoMsg.OnlineUserRequest onlineUserRequest = ProtoMsg.OnlineUserRequest.newBuilder().setFrom(session.getUserId()).build();
        ProtoMsg.Message message = ProtoMsg.Message.newBuilder().setTypeValue(ProtoMsg.MsgType.ONLINE_USER_REQUEST_VALUE).setOnlineUserRequest(onlineUserRequest).setType(ProtoMsg.MsgType.ONLINE_USER_REQUEST).setSessionid(session.getSessionId()).build();
        messageSender.send(message);
    }
}
