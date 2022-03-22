package org.situjunjie.chatclient.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.situjunjie.chatclient.client.SimpleClient;
import org.situjunjie.chatcommon.bean.msg.ProtoMsg;
import org.springframework.stereotype.Service;
import util.ThreadUtil;

import java.util.UUID;

/**
 * @className: ClientBusinessInboundHandler
 * @description: 客户端业务入站处理器
 * @author: situjunjie
 * @date: 2022/3/20
 **/
@Service
@Slf4j
public class ClientBusinessInboundHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SimpleClient.session.setChannel(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ProtoMsg.Message message = (ProtoMsg.Message) msg;
        //1。判断message类型
        switch (message.getType()){
            case LOGIN_RESPONSE:
                ThreadUtil.getMixExecutor().execute(()->{
                    ProtoMsg.LoginReponse loginResponse = message.getLoginResponse();
                    if(loginResponse.getResult()){
                        log.info("登录成功");
                        SimpleClient.session.setSessionId(message.getSessionid());
                        SimpleClient.session.setChannel(ctx.channel());
                    }
                });
                break;
            case MESSAGE_RESPONSE:
                ThreadUtil.getMixExecutor().execute(()->{
                    ProtoMsg.MessageResponse messageResponse = message.getMessageResponse();
                    if(messageResponse.getResult()){
                        log.info(messageResponse.getFrom()+":"+messageResponse.getInfo());
                    }
                });
                break;
            case ONLINE_USER_RESPONSE:
                ThreadUtil.getMixExecutor().execute(()->{
                    ProtoMsg.OnlineUserResponse onlineUserResponse = message.getOnlineUserResponse();
                    if(onlineUserResponse.getResult()){
                        String content = onlineUserResponse.getContent();
                        log.info("当前其他在线用户：{}",content);
                    }
                });
                break;
            default:
                break;
        }
        super.channelRead(ctx, msg);
    }
}
