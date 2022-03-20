package org.situjunjie.chatclient.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.situjunjie.chatclient.command.CommandController;
import org.situjunjie.chatclient.handler.ClientBusinessInboundHandler;
import org.situjunjie.chatclient.handler.ProtobufDecoder;
import org.situjunjie.chatclient.handler.ProtobufEncoder;
import org.situjunjie.chatcommon.client.ClientSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.UUID;

/**
 * @className: SimpleClient
 * @description: 简单的ChatIM客户端
 * @author: situjunjie
 * @date: 2022/3/19
 **/
@Service
@Slf4j
public class SimpleClient {

    @Value("${im.server.ip}")
    private String serverAddr;

    @Value("${im.server.port}")
    private Integer serverPort;

    private EventLoopGroup workerGroup;

    public static final ClientSession session = new ClientSession();

    @Autowired
    ProtobufDecoder protobufDecoder;

    @Autowired
    ProtobufEncoder protobufEncoder;

    @Autowired
    CommandController commandController;

    @Autowired
    ClientBusinessInboundHandler clientBusinessInboundHandler;

    private Scanner sc = new Scanner(System.in);

    public void run(){
        Bootstrap bootstrap = new Bootstrap();
        workerGroup = new NioEventLoopGroup();
        bootstrap.group(workerGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(protobufEncoder)
                                .addLast(protobufDecoder).addLast(clientBusinessInboundHandler);
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(serverAddr, serverPort)).sync();
            if (channelFuture.isSuccess()){
                log.info("连接服务器成功");
                commandController.run();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
