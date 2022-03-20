package org.situjunjie.chatserver.server;

import org.situjunjie.chatserver.handler.BusinessInboundHandler;
import org.situjunjie.chatserver.handler.ProtobufDecoder;
import org.situjunjie.chatserver.handler.ProtobufEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * @className: SimpleChatServer
 * @description: 简单的聊天室服务器
 * @author: situjunjie
 * @date: 2022/3/18
 **/
@Service("simpleChatServer")
@Slf4j
public class SimpleChatServer {

    @Value("${server.port}")
    private Integer port;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;


    /**
     * 服务器启动引导
     */
    public void run() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        bootstrap.channel(NioServerSocketChannel.class)
                .group(bossGroup,workerGroup)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.SO_BACKLOG,128)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ProtobufEncoder())
                                .addLast(new ProtobufDecoder())
                                .addLast(new BusinessInboundHandler());
                    }
                });

        try {
            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(port)).sync();
                if(channelFuture.isSuccess()){
                    log.info("服务器监听端口{}成功",port);
                }
                channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
