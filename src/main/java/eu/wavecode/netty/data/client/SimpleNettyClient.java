package eu.wavecode.netty.data.client;

import eu.wavecode.netty.data.packet.handler.PacketChannelInboundHandler;
import eu.wavecode.netty.data.packet.handler.PacketDecoder;
import eu.wavecode.netty.data.packet.handler.PacketEncoder;
import eu.wavecode.netty.data.registry.IPacketRegistry;
import eu.wavecode.netty.data.registry.SimplePacketRegistry;
import eu.wavecode.netty.data.server.SslUtil;
import io.netty5.bootstrap.Bootstrap;
import io.netty5.channel.*;
import io.netty5.channel.nio.NioHandler;
import io.netty5.channel.socket.SocketChannel;
import io.netty5.channel.socket.nio.NioSocketChannel;
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
public class SimpleNettyClient {

    private String host;
    private int port;
    private int size;

    private boolean ssl;

    private SslContext sslContext;
    private Channel channel;

    private Bootstrap bootstrap;
    private EventLoopGroup group;

    private IPacketRegistry packetRegistry;

    public void createClient() throws Exception {
        if (this.ssl) this.sslContext = SslUtil.createSslContext();
        if (this.packetRegistry == null) this.packetRegistry = new SimplePacketRegistry();
        if (this.size <= 0) this.size = 1024;
        
        this.group = new MultithreadEventLoopGroup(NioHandler.newFactory());

        this.bootstrap = new Bootstrap();
        this.bootstrap.group(this.group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.AUTO_READ, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(final SocketChannel ch) throws Exception {
                        final ChannelPipeline channelPipeline = ch.pipeline();
                        if (SimpleNettyClient.this.sslContext != null)
                            channelPipeline.addLast(SimpleNettyClient.this.sslContext.newHandler(ch.bufferAllocator(), SimpleNettyClient.this.host, SimpleNettyClient.this.port));

                        channelPipeline.addLast(
                                new PacketDecoder(SimpleNettyClient.this.packetRegistry),
                                new PacketEncoder(SimpleNettyClient.this.packetRegistry, SimpleNettyClient.this.size),
                                new PacketChannelInboundHandler());
                    }
                });
    }

    public void connect() throws Exception {
        this.channel = this.bootstrap.connect(this.host, this.port).asStage().get();
        this.channel.closeFuture().asStage().sync();
    }

    public void write(final Object object) {
        System.out.println("Writing: " + object.getClass().getSimpleName());
        this.channel.writeAndFlush(object);
        System.out.println("Wrote: " + object.getClass().getSimpleName());
    }

    public void close() {
        this.group.shutdownGracefully();
    }
}
