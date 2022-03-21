package org.situjunjie.chatserver.processor;

import io.netty.channel.Channel;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;
import org.situjunjie.chatserver.sender.MessageSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @className: ChatProcessor
 * @description: 聊天处理类
 * @author: situjunjie
 * @date: 2022/3/21
 **/
@Service
public class ChatProcessor {

    public void handleChant(ProtoMsg.Message message, Channel channel) {
        //解析message
        ProtoMsg.MessageRequest messageRequest = message.getMessageRequest();
        if(StringUtils.isEmpty(messageRequest.getTo())){
            //群发消息
            MessageSender.sendMessageToOther(channel,messageRequest.getContent(),messageRequest.getFrom());
        }
    }
}
