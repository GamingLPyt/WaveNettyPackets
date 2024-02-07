package eu.wavecode.netty.data.server;

import eu.wavecode.netty.data.packet.handler.PacketChannelInboundHandler;
import eu.wavecode.netty.data.packet.handler.PacketDecoder;
import eu.wavecode.netty.data.packet.handler.PacketEncoder;
import eu.wavecode.netty.data.registry.IPacketRegistry;
import eu.wavecode.netty.data.registry.SimplePacketRegistry;
import io.netty5.bootstrap.ServerBootstrap;
import io.netty5.channel.*;
import io.netty5.channel.nio.NioHandler;
import io.netty5.channel.socket.SocketChannel;
import io.netty5.channel.socket.nio.NioServerSocketChannel;
import io.netty5.handler.logging.LogLevel;
import io.netty5.handler.logging.LoggingHandler;
import io.netty5.handler.ssl.SslContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 05, 2024
 * #      Time >> 20:59
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleNettyServer {

    private int port;
    private int size;

    private boolean ssl;

    private SslContext sslContext;
    private Channel channel;

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private IPacketRegistry packetRegistry;

    public void createServer() throws Exception {
        if (this.ssl) this.sslContext = SslUtil.createSslContext();
        if (this.packetRegistry == null) this.packetRegistry = new SimplePacketRegistry();
        if (this.size <= 0) this.size = 1024;

        this.bossGroup = new MultithreadEventLoopGroup(1, NioHandler.newFactory());
        this.workerGroup = new MultithreadEventLoopGroup(NioHandler.newFactory());

        this.serverBootstrap = new ServerBootstrap()
                .group(this.bossGroup, this.workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.AUTO_READ, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(final SocketChannel ch) {
                        final ChannelPipeline channelPipeline = ch.pipeline();
                        if (SimpleNettyServer.this.sslContext != null)
                            channelPipeline.addLast(SimpleNettyServer.this.sslContext.newHandler(ch.bufferAllocator()));

                        channelPipeline.addLast(
                                new PacketDecoder(SimpleNettyServer.this.packetRegistry),
                                new PacketEncoder(SimpleNettyServer.this.packetRegistry, SimpleNettyServer.this.size),
                                new PacketChannelInboundHandler());
                    }
                });
    }

    public void startServer() throws Exception {
        this.channel = this.serverBootstrap.bind(this.port).asStage().get();
        this.channel.closeFuture().asStage().sync();
    }

    public void write(final Channel channel, final Object msg) {
        channel.writeAndFlush(msg);
    }

    public void stopServer() {
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }
}
