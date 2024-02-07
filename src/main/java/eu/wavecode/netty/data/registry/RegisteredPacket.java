package eu.wavecode.netty.data.registry;

import eu.wavecode.netty.data.packet.Packet;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:01
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public class RegisteredPacket {

    private final Class<? extends Packet> packetClass;
    private final Constructor<? extends Packet> constructor;

    public RegisteredPacket(final Class<? extends Packet> packetClass) throws NoSuchMethodException {
        this.packetClass = packetClass;

        final List<Constructor<?>> emptyConstructorList = Arrays.stream(packetClass.getConstructors()).filter(constructor -> constructor.getParameterCount() == 0).toList();
        if (emptyConstructorList.isEmpty()) throw new NoSuchMethodException("Packet is missing no-args-constructor");
        this.constructor = (Constructor<? extends Packet>) emptyConstructorList.get(0);
    }

    public Class<? extends Packet> getPacketClass() {
        return this.packetClass;
    }

    public Constructor<? extends Packet> getConstructor() {
        return this.constructor;
    }
}
