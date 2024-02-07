package eu.wavecode.netty.data.packet;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 05, 2024
 * #      Time >> 22:19
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public interface CallableDecoder<T> {

    T read(PacketBuffer buffer);
}
