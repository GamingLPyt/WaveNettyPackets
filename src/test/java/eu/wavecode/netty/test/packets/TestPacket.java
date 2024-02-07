package eu.wavecode.netty.test.packets;

import eu.wavecode.netty.data.packet.Packet;
import eu.wavecode.netty.data.packet.PacketBuffer;
import lombok.Data;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:32
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */

@Data
public class TestPacket extends Packet {

    private String message;

    @Override
    public void read(final PacketBuffer buffer) {
        this.message = buffer.readUTF8();
    }

    @Override
    public void write(final PacketBuffer buffer) {
        buffer.writeUTF8(this.message);
    }
}
