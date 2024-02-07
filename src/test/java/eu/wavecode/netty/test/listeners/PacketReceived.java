package eu.wavecode.netty.test.listeners;

import eu.wavecode.netty.data.event.EventTarget;
import eu.wavecode.netty.data.event.events.callable.PacketReceivedEvent;
import eu.wavecode.netty.test.packets.TestPacket;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:34
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public class PacketReceived {

    @EventTarget
    private void onPacketReceived(final PacketReceivedEvent event) {
        if (event.getPacket() instanceof TestPacket) {
            final TestPacket testPacket = (TestPacket) event.getPacket();
            System.out.println("Received message: " + testPacket.getMessage());
        }
    }
}
