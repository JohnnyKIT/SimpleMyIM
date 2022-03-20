package org.situjunjie.chatserver.sender;

import io.netty.channel.Channel;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;

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
}
