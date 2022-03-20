package org.situjunjie.chatserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;
import org.springframework.stereotype.Component;

/**
 * @className: ProtobufEncoder
 * @description: ProtoMsg编码器
 * @author: situjunjie
 * @date: 2022/3/20
 **/
@Component
public class ProtobufEncoder extends MessageToByteEncoder<ProtoMsg.Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ProtoMsg.Message msg, ByteBuf out) throws Exception {
        byte[] bytes = msg.toByteArray();
        out.writeShort(bytes.length);
        out.writeBytes(bytes);
    }
}
