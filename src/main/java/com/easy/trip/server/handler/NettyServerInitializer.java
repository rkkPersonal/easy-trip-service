package com.easy.trip.server.handler;

import com.easy.trip.server.heart.ServerIdleStateTrigger;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Steven
 * @date 2022年10月02日 1:30
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final String PATH = "/ws";
    private static final String PROTOCOL = "/ws";

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        log.info("收到新连接");
        // 流水线管理通道中的处理程序（Handler），用来处理业务
        // webSocket协议本身是基于http协议的，所以这边也要使用http编解码器
        ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(5, 0, 0));
        ch.pipeline().addLast("idleStateTrigger", new ServerIdleStateTrigger());
        ch.pipeline().addLast(new HttpServerCodec());
        ch.pipeline().addLast(new ObjectEncoder());
        // 以块的方式来写的处理器
        ch.pipeline().addLast(new ChunkedWriteHandler());
                /*
                说明：
                1、http数据在传输过程中是分段的，HttpObjectAggregator可以将多个段聚合
                2、这就是为什么，当浏览器发送大量数据时，就会发送多次http请求
                 */
        ch.pipeline().addLast(new HttpObjectAggregator(8192));
                /*
                说明：
                1、对应webSocket，它的数据是以帧（frame）的形式传递
                2、浏览器请求时 ws://localhost:58080/xxx 表示请求的uri
                3、核心功能是将http协议升级为ws协议，保持长连接
                */
        ch.pipeline().addLast(new WebSocketServerProtocolHandler(PATH, PROTOCOL, true, 65536 * 10));
        ch.pipeline().addLast(new MessageWebSocketHandler());
    }
}
