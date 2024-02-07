package eu.wavecode.netty.data.packet;

import java.util.concurrent.ThreadLocalRandom;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 05, 2024
 * #      Time >> 22:26
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public abstract class Packet implements PacketEncoder, PacketDecoder {

    private long sessionId = ThreadLocalRandom.current().nextLong();

    public void setSessionId(final long sessionId) {
        this.sessionId = sessionId;
    }

    public long getSessionId() {
        return this.sessionId;
    }
}
