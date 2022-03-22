package org.situjunjie.chatserver.processor;

import io.netty.channel.Channel;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;
import org.situjunjie.chatcommon.client.ClientSession;
import org.situjunjie.chatserver.sender.MessageSender;
import org.situjunjie.chatserver.server.ClientSessionMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @className: OnlineUserProcessor
 * @description: 在线用户处理器
 * @author: situjunjie
 * @date: 2022/3/22
 **/
@Service
public class OnlineUserProcessor {

    /**
     * 发送客户端当前在线用户列表
     * @param message
     * @param channel
     */
    public void sendOnlineUserList(ProtoMsg.Message message, Channel channel) {
        StringBuilder userList = new StringBuilder();
        Map<String, List<ClientSession>> clientSessionMap = ClientSessionMap.getInstance();
        String userId = message.getOnlineUserRequest().getFrom();
        for (String key : clientSessionMap.keySet()) {
            if(key.equals(userId)){
                continue;
            }
            userList.append(key).append(",");
        }
        if(userList.length()>1){
            userList.deleteCharAt(userList.length()-1);
        }
        ProtoMsg.OnlineUserResponse onlineUserResponse = ProtoMsg.OnlineUserResponse.newBuilder().setContent(userList.toString()).setResult(true).build();
        ProtoMsg.Message msg = ProtoMsg.Message.newBuilder().setOnlineUserResponse(onlineUserResponse).setTypeValue(ProtoMsg.MsgType.ONLINE_USER_RESPONSE_VALUE).setType(ProtoMsg.MsgType.ONLINE_USER_RESPONSE).build();
        MessageSender.sendMessage(channel,msg);
    }
}
