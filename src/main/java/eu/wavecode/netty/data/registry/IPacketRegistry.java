package eu.wavecode.netty.data.registry;

import eu.wavecode.netty.data.exceptions.PacketRegistrationException;
import eu.wavecode.netty.data.packet.Packet;

import java.lang.reflect.InvocationTargetException;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 05, 2024
 * #      Time >> 22:26
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public interface IPacketRegistry {

    void registerPacket(int packetId, Packet packet) throws PacketRegistrationException;

    void registerPacket(int packetId, Class<? extends Packet> packet) throws PacketRegistrationException;

    int getPacketId(Class<? extends Packet> packetClass);

    <T extends Packet> T constructPacket(int packetId) throws InvocationTargetException, InstantiationException, IllegalAccessException;

    boolean containsPacketId(int id);
}
