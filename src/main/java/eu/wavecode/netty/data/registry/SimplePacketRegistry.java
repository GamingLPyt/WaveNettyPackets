package eu.wavecode.netty.data.registry;

import eu.wavecode.netty.data.exceptions.PacketRegistrationException;
import eu.wavecode.netty.data.packet.Packet;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:01
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public class SimplePacketRegistry implements IPacketRegistry {

    private final HashMap<Integer, RegisteredPacket> registeredPackets = new HashMap<>();

    @Override
    public void registerPacket(final int packetId, final Packet packet) throws PacketRegistrationException {
        this.registerPacket(packetId, packet.getClass());
    }

    @Override
    public void registerPacket(final int packetId, final Class<? extends Packet> packet) throws PacketRegistrationException {
        if (this.containsPacketId(packetId)) throw new PacketRegistrationException("PacketID is already in use");
        try {
            final RegisteredPacket registeredPacket = new RegisteredPacket(packet);
            this.registeredPackets.put(packetId, registeredPacket);
        } catch (final NoSuchMethodException e) {
            throw new PacketRegistrationException("Failed to register packet", e);
        }
    }

    @Override
    public int getPacketId(final Class<? extends Packet> packetClass) {
        for (final int packetId : this.registeredPackets.keySet())
            if (this.registeredPackets.get(packetId).getPacketClass().equals(packetClass)) return packetId;

        return -1;
    }

    @Override
    public <T extends Packet> T constructPacket(final int packetId) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return (T) this.registeredPackets.get(packetId).getConstructor().newInstance();
    }

    @Override
    public boolean containsPacketId(final int id) {
        return this.registeredPackets.containsKey(id);
    }
}
