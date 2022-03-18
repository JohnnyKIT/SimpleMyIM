package org.situjunjie.chatserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @className: EchoHandler
 * @description: 简单的回显Handler
 * @author: situjunjie
 * @date: 2022/3/18
 **/
@Slf4j
@Service
public class EchoHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        int len = byteBuf.readableBytes();
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes,0,len);
        log.info("收到客户端消息->{}",new String(bytes, StandardCharsets.UTF_8));
        super.channelRead(ctx, msg);
    }
}
