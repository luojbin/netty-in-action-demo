package com.luojbin.netty.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 收到消息并打印
        ByteBuf buf = (ByteBuf) msg;
        String msgStr = buf.toString(StandardCharsets.UTF_8);
        System.out.println("EchoServer 收到消息: " + msgStr);

        // 给消息来源回信, 但是没有执行 flush, 即回信暂时放在缓冲区
        // dev_note 这里必须使用 ByteBuf 类型进行回信, 否则客户端收不到回信, 这个应该跟客户端 handler 的类型有关
        ctx.write(Unpooled.copiedBuffer("必须用buffer?", StandardCharsets.UTF_8));
        // ctx.write("直接字符串");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 回信并冲刷缓冲区, 即将回信发出, 然后关闭本次连接
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
