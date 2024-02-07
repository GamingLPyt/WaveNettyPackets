package eu.wavecode.netty.data.packet.handler;

import eu.wavecode.netty.data.packet.Packet;
import eu.wavecode.netty.data.packet.PacketBuffer;
import eu.wavecode.netty.data.registry.IPacketRegistry;
import io.netty5.buffer.Buffer;
import io.netty5.channel.ChannelHandlerContext;
import io.netty5.handler.codec.EncoderException;
import io.netty5.handler.codec.MessageToByteEncoder;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:24
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    private final IPacketRegistry packetRegistry;
    private final int size;

    public PacketEncoder(final IPacketRegistry packetRegistry, final int size) {
        this.packetRegistry = packetRegistry;
        this.size = size;
    }

    @Override
    protected Buffer allocateBuffer(final ChannelHandlerContext channelHandlerContext, final Packet packet) throws Exception {
        return channelHandlerContext.bufferAllocator().allocate(this.size);
    }

    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Packet packet, final Buffer buffer) throws Exception {
        final int packetId = this.packetRegistry.getPacketId(packet.getClass());

        if (packetId < 0) throw new EncoderException("Returned PacketId by registry is < 0");

        buffer.writeInt(packetId);
        buffer.writeLong(packet.getSessionId());

        final PacketBuffer packetBuffer = new PacketBuffer(buffer);
        packet.write(packetBuffer);
        buffer.writeBytes(packetBuffer);
    }
}
