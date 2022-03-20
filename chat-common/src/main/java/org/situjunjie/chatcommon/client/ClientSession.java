package org.situjunjie.chatcommon.client;


import io.netty.channel.Channel;
import lombok.Data;

/**
 * @className: ClientSession
 * @description: 客户端会话信息
 * @author: situjunjie
 * @date: 2022/3/20
 **/
@Data
public class ClientSession {

    private String sessionId;

    private String userId;

    private Channel channel;
}
