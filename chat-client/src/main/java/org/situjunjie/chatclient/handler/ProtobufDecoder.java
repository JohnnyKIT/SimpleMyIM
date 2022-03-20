package org.situjunjie.chatclient.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: ProtobufDecoder
 * @description: Protobuf解码器
 * @author: situjunjie
 * @date: 2022/3/20
 **/
@Component
public class ProtobufDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();//标志一下
        if(in.readableBytes()<2){ //校验包头
            return ;
        }
        short len = in.readShort();
        if(in.readableBytes()<len){ //可读长度不够，会发生半包问题
            in.resetReaderIndex();
            return ;
        }
        //长度够用 读取数据
        byte[] data = new byte[len];
        in.readBytes(data,0,len);
        ProtoMsg.Message message = ProtoMsg.Message.parseFrom(data);
        out.add(message);

    }
}
