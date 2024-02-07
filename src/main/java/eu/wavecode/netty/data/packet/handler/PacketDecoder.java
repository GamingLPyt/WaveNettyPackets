package eu.wavecode.netty.data.packet.handler;

import eu.wavecode.netty.data.packet.Packet;
import eu.wavecode.netty.data.packet.PacketBuffer;
import eu.wavecode.netty.data.registry.IPacketRegistry;
import io.netty5.buffer.Buffer;
import io.netty5.channel.ChannelHandlerContext;
import io.netty5.handler.codec.ByteToMessageDecoder;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 05, 2024
 * #      Time >> 22:25
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public class PacketDecoder extends ByteToMessageDecoder {

    private final IPacketRegistry packetRegistry;

    public PacketDecoder(final IPacketRegistry packetRegistry) {
        this.packetRegistry = packetRegistry;

        System.out.println("DECODER: PacketDecoder created");
    }

    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Buffer buffer) throws Exception {
        if (buffer.readableBytes() < 4) {
            System.out.println("DECODER: Not enough readable bytes (" + buffer.readableBytes() + ")");
            return;
        }

        final int packetId = buffer.readInt();

        if (!this.packetRegistry.containsPacketId(packetId)) return;
        final long sessionId = buffer.readLong();
        final PacketBuffer packetBuffer = new PacketBuffer(buffer.copy());
        final Packet packet = this.packetRegistry.constructPacket(packetId);
        packet.setSessionId(sessionId);
        packet.read(packetBuffer);

        buffer.resetOffsets();
    }
}
