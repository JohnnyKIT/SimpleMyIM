package org.situjunjie.chatserver.sender;

import io.netty.channel.Channel;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;
import org.situjunjie.chatcommon.client.ClientSession;
import org.situjunjie.chatserver.server.ClientSessionMap;
import org.situjunjie.chatserver.server.SimpleChatServer;

import java.util.List;
import java.util.Map;

/**
 * @className: MessageSender
 * @description: 发送消息
 * @author: situjunjie
 * @date: 2022/3/20
 **/
public class MessageSender {

    public static void sendMessage(Channel channel, ProtoMsg.Message message){
        channel.writeAndFlush(message);
    }

    public static void sendMessageToOther(Channel channel,String content, String from) {
        Map<String, List<ClientSession>> instance = ClientSessionMap.getInstance();
        for (String userId : instance.keySet()) {
            if(userId.equals(from)) {
                continue;
            }
            ProtoMsg.MessageResponse messageResponse = ProtoMsg.MessageResponse.newBuilder().setFrom(from).setResult(true).setInfo(content)
                    .build();
            ProtoMsg.Message message = ProtoMsg.Message.newBuilder().setMessageResponse(messageResponse).setType(ProtoMsg.MsgType.MESSAGE_RESPONSE)
                    .setTypeValue(ProtoMsg.MsgType.MESSAGE_RESPONSE_VALUE).build();
            List<ClientSession> clientSessions = instance.get(userId);
            for (ClientSession clientSession : clientSessions) {
                sendMessage(clientSession.getChannel(),message);

            }
        }

    }
}
