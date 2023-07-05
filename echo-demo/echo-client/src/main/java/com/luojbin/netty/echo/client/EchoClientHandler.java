package com.luojbin.netty.echo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 网络连接被建立时, 发送消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好, 你的小财神上线了", StandardCharsets.UTF_8));
    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        // 打印收到的消息
        if (msg instanceof ByteBuf) {
            System.out.println("客户端 channelRead0 收到回信:" + ((ByteBuf)msg).toString(StandardCharsets.UTF_8));
        } else {
            System.out.println("其他类型消息: "+ msg.getClass().getSimpleName());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            System.out.println("客户端 channelRead 收到回信:" + ((ByteBuf)msg).toString(StandardCharsets.UTF_8));
        } else {
            System.out.println("其他类型消息: "+ msg.getClass().getSimpleName());
        }
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
