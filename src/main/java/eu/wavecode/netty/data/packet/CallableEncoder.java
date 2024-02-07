package eu.wavecode.netty.data.packet;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 05, 2024
 * #      Time >> 22:20
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public interface CallableEncoder<T> {

    void write(T data, PacketBuffer buffer);
}
