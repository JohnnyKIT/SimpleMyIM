package org.situjunjie.chatclient.processor;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.situjunjie.chatclient.client.SimpleClient;
import org.situjunjie.chatclient.sender.MessageSender;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;

/**
 * @className: ChatProcssor
 * @description: 聊天处理器
 * @author: situjunjie
 * @date: 2022/3/21
 **/
@Slf4j
public class ChatProcssor {

    private MessageSender messageSender = new MessageSender();

    public void sendCommonChatMssage(String message){
        //组装通用聊天信息
        String userId = SimpleClient.session.getUserId();
        String sessionId = SimpleClient.session.getSessionId();
        ProtoMsg.MessageRequest messageRequest = ProtoMsg.MessageRequest.newBuilder().setContent(message).setFrom(userId).build();
        ProtoMsg.Message msg = ProtoMsg.Message.newBuilder().setSessionid(sessionId).setTypeValue(ProtoMsg.MsgType.MESSAGE_REQUEST_VALUE)
                .setType(ProtoMsg.MsgType.MESSAGE_REQUEST).setMessageRequest(messageRequest).build();
        messageSender.send(msg);
    }

    public void sendPrivateMessage(String message, String userId) {
        //组装私聊消息
        String fromId = SimpleClient.session.getUserId();
        String sessionId = SimpleClient.session.getSessionId();
        ProtoMsg.MessageRequest messageRequest = ProtoMsg.MessageRequest.newBuilder().setContent(message).setFrom(fromId).setTo(userId).build();
        ProtoMsg.Message msg = ProtoMsg.Message.newBuilder().setSessionid(sessionId).setTypeValue(ProtoMsg.MsgType.MESSAGE_REQUEST_VALUE)
                .setType(ProtoMsg.MsgType.MESSAGE_REQUEST).setMessageRequest(messageRequest).build();
        messageSender.send(msg);
    }
}
