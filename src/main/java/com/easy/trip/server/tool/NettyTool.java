package com.easy.trip.server.tool;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Steven
 * @date 2022年10月02日 2:02
 */
public class NettyTool {

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final ConcurrentHashMap<String, Channel> userChannelMap=new ConcurrentHashMap<>();

    public static ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    public static ConcurrentHashMap<String, Channel> getUserChannelMap(){
        return userChannelMap;
    }



}
