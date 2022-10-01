package com.easy.trip.server.task;

import com.easy.trip.server.tool.NettyTool;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Steven
 * @date 2022年10月02日 2:26
 */
@Component
public class MessagePublish {

    public void pushMsgToOne(String userId, String msg) {
        ConcurrentHashMap<String, Channel> userChannelMap = NettyTool.getUserChannelMap();
        Channel channel = userChannelMap.get(userId);
        channel.writeAndFlush(new TextWebSocketFrame(msg));
    }

    @Scheduled(cron = "0/9 * * * * ?")
    public void pushMsgToAll() {
        NettyTool.getChannelGroup().writeAndFlush(new TextWebSocketFrame("天气不好，注意安全行驶!"));
    }

}
