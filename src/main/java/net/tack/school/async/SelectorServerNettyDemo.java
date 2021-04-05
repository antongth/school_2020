package net.thumbtack.school.async;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class SelectorServerNettyDemo {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup mainEvenExecutor = new NioEventLoopGroup(1);
        EventLoopGroup childEvenExecutors = new NioEventLoopGroup(1);

        try {
            new ServerBootstrap()
                    .group(mainEvenExecutor, childEvenExecutors)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new Init())
                    .bind(8080).sync().channel().closeFuture().sync();
        } finally {
            mainEvenExecutor.shutdownGracefully();
            childEvenExecutors.shutdownGracefully();
        }

    }

    private static class Init extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) {
            ch.pipeline()
                    .addLast(new HttpRequestDecoder())
                    .addLast(new HttpObjectAggregator(100 * 1024))
                    .addLast(new HttpResponseEncoder())
                    .addLast(new SelectorServerNettyDemoHandler());
        }
    }

    private static class SelectorServerNettyDemoHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        private static final String CONTENT =
                        "<html>" +
                        "<body>" +
                        "<h1>Hello, %s!</h1>" +
                        "</body>" +
                        "</html>";

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {

            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
            String username = queryStringDecoder.parameters().get("username").get(0);

            System.out.println(username);
            String content = String.format(CONTENT, username);

            FullHttpResponse response = new DefaultFullHttpResponse(
                    HTTP_1_1,
                    OK,
                    Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));
            response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(CONNECTION, "close");

            ctx.write(response);
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
