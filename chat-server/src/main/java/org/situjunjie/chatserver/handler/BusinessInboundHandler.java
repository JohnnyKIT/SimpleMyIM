package org.situjunjie.chatserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;
import org.situjunjie.chatserver.processor.ChatProcessor;
import org.situjunjie.chatserver.processor.LoginProcessor;
import org.situjunjie.chatserver.processor.OnlineUserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.ThreadUtil;

import java.util.concurrent.Executor;

/**
 * @className: BusinessInboundHandler
 * @description: 业务入站处理器
 * @author: situjunjie
 * @date: 2022/3/20
 **/
@Component
public class BusinessInboundHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    LoginProcessor loginProcessor = new LoginProcessor();

    @Autowired
    ChatProcessor chatProcessor = new ChatProcessor();

    @Autowired
    OnlineUserProcessor onlineUserProcessor = new OnlineUserProcessor();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ProtoMsg.Message message = (ProtoMsg.Message) msg;
        Executor executor = ThreadUtil.getMixExecutor();
        executor.execute(()->{
            //1.判断Message类型
            ProtoMsg.MsgType msgType = message.getType();
            switch (msgType){
                case LOGIN_REQUEST://登录请求
                    loginProcessor.login(message,ctx.channel());
                    break;
                case MESSAGE_REQUEST:
                    chatProcessor.handleChant(message,ctx.channel());
                    break;
                case ONLINE_USER_REQUEST:
                    onlineUserProcessor.sendOnlineUserList(message,ctx.channel());
                default:
                    break;
            }
        });
        super.channelRead(ctx, msg);
    }
}
