package com.easy.trip.server.boot;

import com.easy.trip.server.handler.NettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Steven
 * @date 2022年09月25日 22:47
 */
@Slf4j
@Component
public class NioNettyServer {

    private NioEventLoopGroup boss;

    private NioEventLoopGroup workPool;

    public NioNettyServer() {
        boss = new NioEventLoopGroup(10);
        workPool = new NioEventLoopGroup(20);
    }

    public void startServer() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, workPool)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer())         // 连接到达时会创建一个通道
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = serverBootstrap.bind(8089).sync(); // (7)
            log.info("MessgePushServer start port :{}", 8089);
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } catch (InterruptedException exception) {
            log.error("message push server error :{}", exception);
        }
    }

    @PreDestroy
    public void colse() {
        boss.shutdownGracefully();
        workPool.shutdownGracefully();
    }

    @PostConstruct
    public void start() {
        new Thread(()->{
            new NioNettyServer().startServer();
        }).start();

    }
}
