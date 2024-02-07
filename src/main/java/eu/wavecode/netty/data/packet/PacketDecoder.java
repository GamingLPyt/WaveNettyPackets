package eu.wavecode.netty.data.packet;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 05, 2024
 * #      Time >> 22:18
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public interface PacketDecoder {

    void read(PacketBuffer buffer);
}
