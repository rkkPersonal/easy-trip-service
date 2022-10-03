package com.easy.trip.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import org.apache.curator.RetryPolicy;

import java.util.concurrent.TimeUnit;

/**
 * @author Steven
 * @date 2022年10月03日 1:01
 */
@ChannelHandler.Sharable
public class ReconnectHandler extends ChannelInboundHandlerAdapter {

    private int retries = 0;
    private RetryPolicy retryPolicy;

    private TcpClient tcpClient;

    public ReconnectHandler(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Successfully established a connection to the server.");
        retries = 0;
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (retries == 0) {
            System.err.println("Lost the TCP connection with the server.");
            ctx.close();
        }

        boolean allowRetry = getRetryPolicy().allowRetry(1,1,null);
        if (allowRetry) {

            /*long sleepTimeMs = getRetryPolicy().getSleepTimeMs(retries);*/

            System.out.println(String.format("Try to reconnect to the server after %dms. Retry count: %d.", 1, ++retries));

            final EventLoop eventLoop = ctx.channel().eventLoop();
            eventLoop.schedule(() -> {
                System.out.println("Reconnecting ...");
                tcpClient.connect();
            }, 1, TimeUnit.MILLISECONDS);
        }
        ctx.fireChannelInactive();
    }


    private RetryPolicy getRetryPolicy() {
        if (this.retryPolicy == null) {
            this.retryPolicy = tcpClient.getRetryPolicy();
        }
        return this.retryPolicy;
    }
}
