package eu.wavecode.netty.test;

import eu.wavecode.netty.data.client.SimpleNettyClient;
import eu.wavecode.netty.data.event.EventManager;
import eu.wavecode.netty.data.registry.IPacketRegistry;
import eu.wavecode.netty.data.registry.SimplePacketRegistry;
import eu.wavecode.netty.test.listeners.PacketReceived;
import eu.wavecode.netty.test.packets.TestPacket;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 05, 2024
 * #      Time >> 21:37
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */

public class TestSimpleNettyClient {

    public static void main(final String[] args) throws Exception {
        final IPacketRegistry packetRegistry = new SimplePacketRegistry();
        packetRegistry.registerPacket(1, new TestPacket());

        EventManager.register(new PacketReceived());

        final SimpleNettyClient simpleNettyClient = SimpleNettyClient.builder()
                .host("localhost")
                .port(8080)
                .ssl(false)
                .packetRegistry(packetRegistry)
                .build();

        simpleNettyClient.createClient();
        new Thread(simpleNettyClient);
        simpleNettyClient.connect();
    }
}
